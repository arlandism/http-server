package com.arlandis;

import com.arlandis.Responses.*;
import com.arlandis.interfaces.*;

import java.util.HashMap;

public class HttpResponseBuilder implements ResponseBuilder {

    private ResourceRetriever retriever;
    private FileResponseFactory fileResponseFactory;
    private TTTService tttService;

    public HttpResponseBuilder(ResourceRetriever retriever, FileResponseFactory factory, TTTService tttService) {
        this.retriever = retriever;
        this.fileResponseFactory = factory;
        this.tttService = tttService;
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
        HashMap<String, Response> responseMap = buildResponseMap(request);
        HashMap<String, Boolean> directoryMap = buildFeatureDirectory(parser);

        if (enabledFeatureRequest(request, directoryMap)) {
            response = getResponseFromMap(request, responseMap);
        } else if (enabledResourceRequest(request, directoryMap)){
            response = fileResponseFactory.fileResponse(request, retriever);
        } else {
            response = new FeatureNotFoundResponse();
        }

        return response;
    }

    private HashMap<String, Boolean> buildFeatureDirectory(FeatureParser parser) {
        HashMap<String, Boolean> routesToEnabled = new HashMap<String, Boolean>();
        routesToEnabled.put("GET /ping", parser.pingValue());
        routesToEnabled.put("GET /form", parser.formValue());
        routesToEnabled.put("POST /form", parser.postFormValue());
        routesToEnabled.put("GET /ping?sleep", parser.sleepValue());
        routesToEnabled.put("GET /game", parser.gameValue());
        routesToEnabled.put("GET /browse", parser.browseValue());
        return routesToEnabled;
    }

    private HashMap<String, Response> buildResponseMap(Request request) {
        HashMap<String, Response> routesToResponses = new HashMap<String, Response>();
        routesToResponses.put("GET /ping", new PongResponse());
        routesToResponses.put("GET /form", new GetFormResponse());
        routesToResponses.put("POST /form", new PostFormResponse(request));
        routesToResponses.put("GET /ping?sleep", new SleepResponse(request, new ThreadSleeper()));
        routesToResponses.put("GET /game", new GameResponse(tttService, request));
        return routesToResponses;
    }

    private Boolean enabledResourceRequest(Request request, HashMap<String, Boolean> routesToEnabled){
        return isResourceRequest(request) && routesToEnabled.get("GET /browse");
    }

    private Boolean enabledFeatureRequest(Request request, HashMap<String, Boolean> routesToEnabled){
        return featureEnabled(request, routesToEnabled) && !isResourceRequest(request);
    }

    private Boolean featureEnabled(Request request, HashMap<String, Boolean> routesToEnabled) {
        String requestedFeature = request.headers();
        for (String route: routesToEnabled.keySet()){
            if (requestedFeature.startsWith(route))
                return routesToEnabled.get(route);
        }
        return false;
    }

    private Response getResponseFromMap(Request request, HashMap<String, Response> routesToResponses){
        Response response = null;
        for (String route: routesToResponses.keySet()){
            if (request.headers().startsWith(route))
                response = routesToResponses.get(route);
        }
        return response;

    }

    private Boolean isResourceRequest(Request request) {
        return request.headers().startsWith("GET /browse");
    }

}