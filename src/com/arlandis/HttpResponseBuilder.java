package com.arlandis;

import com.arlandis.Responses.*;
import com.arlandis.interfaces.*;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseBuilder implements ResponseBuilder {

    private ResourceRetriever retriever;
    private FileResponseFactory fileResponseFactory;
    private TTTService tttService;
    private Map<String, Response> routesToResponses = new HashMap<String, Response>();

    public HttpResponseBuilder(ResourceRetriever retriever, FileResponseFactory factory, TTTService tttService) {
        this.retriever = retriever;
        this.fileResponseFactory = factory;
        this.tttService = tttService;
    }

    public String generateResponse(Request request, Inventory featureInventory) {
        return getResponseString(request, featureInventory);
    }

    private String getResponseString(Request request, Inventory featureInventory) {
        String responseString;
        Response response;
        if (featureEnabled(request, featureInventory)) {
            responseString = respondToRequest(request);
        } else {
            response = new FeatureNotFoundResponse();
            responseString = createResponseString(response);
        }
        return responseString;
    }

    private Boolean featureEnabled(Request request, Inventory featureInventory) {
        String requestedFeature = request.headers();
        return featureInventory.isEnabled(requestedFeature);
    }

    private String respondToRequest(Request request) {
        routesToResponses.put("GET /ping", new PongResponse());
        routesToResponses.put("GET /form", new GetFormResponse());
        routesToResponses.put("POST /form", new PostFormResponse(request));
        routesToResponses.put("GET /ping?sleep", new SleepResponse(request, new ThreadSleeper()));
        routesToResponses.put("GET /game", new GameResponse(tttService, request));

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