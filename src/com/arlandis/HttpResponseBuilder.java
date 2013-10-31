package com.arlandis;

import com.arlandis.Responses.*;
import com.arlandis.interfaces.*;

public class HttpResponseBuilder implements ResponseBuilder {

    private ResourceRetriever retriever;
    private FileResponseFactory fileResponseFactory;
    private TTTService service;

    public HttpResponseBuilder(ResourceRetriever retriever, FileResponseFactory factory, TTTService service) {
        this.retriever = retriever;
        this.fileResponseFactory = factory;
        this.service = service;
    }

    @Override
    public String generateResponse(Request request) {
        return respondToRequest(request);
    }

    public String generateResponse(Request request, Toggler toggler) {
        Response response;
        String returnVal;
        if (featureEnabled(request, toggler)) {
            returnVal = respondToRequest(request);
        } else {
            response = new FeatureNotFoundResponse();
            returnVal = createResponseString(response);
        }
        return returnVal;
    }

    private Boolean featureEnabled(Request request, Toggler toggler) {
        return toggler.isEnabled(request.headers());
    }

    private String respondToRequest(Request request) {

        Response response;

        if (isFormRequest(request)) {

            response = new GetFormResponse();

        } else if (isPostRequest(request)) {

            response = new PostFormResponse(request);

        } else if (isSleepRequest(request)) {

            response = new SleepResponse(request, new ThreadSleeper());

        } else if (isResourceRequest(request)) {

            response = fileResponseFactory.fileResponse(request, retriever);

        } else if (isServiceRequest(request)) {

            response = new ServiceResponse(service, request);

        } else if (isPongRequest(request)) {

            response = new PongResponse();

        } else {

            response = new FeatureNotFoundResponse();

        }

        return createResponseString(response);

    }

    private String createResponseString(Response response) {
        return "HTTP/1.0 200 OK" + "\r\n" + "Content-type: " + response.contentType() + "\r\n\r\n" + response.body();
    }

    private boolean isPongRequest(Request request) {
        return request.headers().startsWith("GET /ping");
    }

    private boolean isFormRequest(Request request) {
        return request.headers().startsWith("GET /form");
    }

    private boolean isPostRequest(Request request) {
        return request.headers().startsWith("POST /form");
    }

    private boolean isSleepRequest(Request request) {
        return request.headers().startsWith("GET /ping?sleep");
    }

    private boolean isResourceRequest(Request request) {
        return request.headers().startsWith("GET /browse");
    }

    private boolean isServiceRequest(Request request) {
        return request.headers().startsWith("GET /game");
    }
}