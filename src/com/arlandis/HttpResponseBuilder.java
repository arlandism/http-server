package com.arlandis;

import com.arlandis.Responses.*;
import com.arlandis.interfaces.*;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseBuilder implements ResponseBuilder {

    private ResourceRetriever retriever;
    private FileResponseFactory fileResponseFactory;
    private TTTService service;
    private Map<String, Response> routesToResponses = new HashMap<String, Response>();

    public HttpResponseBuilder(ResourceRetriever retriever, FileResponseFactory factory, TTTService service) {
        this.retriever = retriever;
        this.fileResponseFactory = factory;
        this.service = service;
    }

    public String generateResponse(Request request, Toggler toggler) {
        return getResponseString(request, toggler);
    }

    private String getResponseString(Request request, Toggler toggler) {
        String responseString;
        Response response;
        if (featureEnabled(request, toggler)) {
            responseString = respondToRequest(request);
        } else {
            response = new FeatureNotFoundResponse();
            responseString = createResponseString(response);
        }
        return responseString;
    }

    private Boolean featureEnabled(Request request, Toggler toggler) {
        return toggler.isEnabled(request.headers());
    }

    private String respondToRequest(Request request) {
        routesToResponses.put("GET /ping", new PongResponse());
        routesToResponses.put("GET /form", new GetFormResponse());
        routesToResponses.put("POST /form", new PostFormResponse(request));
        routesToResponses.put("GET /ping?sleep", new SleepResponse(request, new ThreadSleeper()));
        routesToResponses.put("GET /game", new ServiceResponse(service, request));

        Response response = getResponse(request);

        return createResponseString(response);

    }

    private String createResponseString(Response response) {
        return "HTTP/1.0 200 OK" + "\r\n" + "Content-type: " + response.contentType() + "\r\n\r\n" + response.body();
    }

    private Response getResponse(Request request){
        Response response = null;
        for (String route: routesToResponses.keySet()){
            if (request.headers().startsWith(route)){
                response = routesToResponses.get(route);
            }
        }

        if (isResourceRequest(request)){
            response = fileResponseFactory.fileResponse(request, retriever);
        } else if (response == null){
            response = new FeatureNotFoundResponse();
        }

        return response;

    }

    private boolean isResourceRequest(Request request) {
        return request.headers().startsWith("GET /browse");
    }
}