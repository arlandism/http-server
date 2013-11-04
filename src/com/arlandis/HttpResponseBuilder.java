package com.arlandis;

import com.arlandis.Responses.*;
import com.arlandis.interfaces.*;

import java.util.LinkedHashMap;

public class HttpResponseBuilder implements ResponseBuilder {

    private ResourceRetriever retriever;
    private FileResponseFactory fileResponseFactory;
    private TTTService tttService;
    private Sleeper sleeper;

    public HttpResponseBuilder(ResourceRetriever retriever, FileResponseFactory factory,
                               TTTService tttService, Sleeper sleeper) {
        this.retriever = retriever;
        this.fileResponseFactory = factory;
        this.tttService = tttService;
        this.sleeper = sleeper;
    }

    public String generateResponse(Request request, FeatureParser parser) {
        Response response;
        response = routeRequest(request, parser);
        return createResponseString(response);
    }

    private String createResponseString(Response response) {
        return "HTTP/1.0 200 OK" + "\r\n" + "Content-type: " + response.contentType() + "\r\n\r\n" + response.body();
    }

    private Response routeRequest(Request request, FeatureParser parser) {
        Response response;
        LinkedHashMap<String, Response> responseMap = buildResponseMap(request);
        LinkedHashMap<String, Boolean> directoryMap = buildFeatureDirectory(parser);

        if (enabledFeatureRequest(request, directoryMap)) {
            response = getResponseFromMap(request, responseMap);

        } else if (enabledResourceRequest(request, directoryMap)){
            response = fileResponseFactory.fileResponse(request, retriever);

        } else {
            response = new FeatureNotFoundResponse();
        }


        return response;
    }

    private LinkedHashMap<String, Boolean> buildFeatureDirectory(FeatureParser parser) {
        LinkedHashMap<String, Boolean> routesToEnabled = new LinkedHashMap<String, Boolean>();
        routesToEnabled.put("GET /ping?sleep", parser.sleepValue());
        routesToEnabled.put("GET /ping", parser.pingValue());
        routesToEnabled.put("GET /form", parser.formValue());
        routesToEnabled.put("POST /form", parser.postFormValue());
        routesToEnabled.put("GET /game", parser.gameValue());
        routesToEnabled.put("GET /browse", parser.browseValue());
        return routesToEnabled;
    }

    private LinkedHashMap<String, Response> buildResponseMap(Request request) {
        LinkedHashMap<String, Response> routesToResponses = new LinkedHashMap<String, Response>();
        routesToResponses.put("GET /ping?sleep", new SleepResponse(request, sleeper));
        routesToResponses.put("GET /ping", new PongResponse());
        routesToResponses.put("GET /form", new GetFormResponse());
        routesToResponses.put("POST /form", new PostFormResponse(request));
        routesToResponses.put("GET /game", new GameResponse(tttService, request));
        return routesToResponses;
    }

    private Boolean enabledResourceRequest(Request request, LinkedHashMap<String, Boolean> routesToEnabled){
        return isResourceRequest(request) && routesToEnabled.get("GET /browse");
    }

    private Boolean enabledFeatureRequest(Request request, LinkedHashMap<String, Boolean> routesToEnabled){
        return featureEnabled(request, routesToEnabled) && !isResourceRequest(request);
    }

    private Boolean featureEnabled(Request request, LinkedHashMap<String, Boolean> routesToEnabled) {
        String requestedFeature = request.headers();
        for (String route: routesToEnabled.keySet()){

            if (requestedFeature.startsWith(route))
                return routesToEnabled.get(route);
        }
        return false;
    }

    private Response getResponseFromMap(Request request, LinkedHashMap<String, Response> routesToResponses){
        Response response = null;
        for (String route: routesToResponses.keySet()){
            if (request.headers().startsWith(route))
                return routesToResponses.get(route);
        }
        return response;

    }

    private Boolean isResourceRequest(Request request) {
        return request.headers().startsWith("GET /browse");
    }

}