package com.arlandis;

import com.arlandis.Responses.*;
import com.arlandis.interfaces.*;

public class HttpResponseBuilder implements ResponseBuilder {

    private ResourceRetriever retriever;
    private FileResponseFactory fileResponseFactory;

    public HttpResponseBuilder(ResourceRetriever retriever, FileResponseFactory factory) {
        this.retriever = retriever;
        this.fileResponseFactory = factory;
    }

    @Override
    public String generateResponse(Request request) {
        return getBody(request);
    }

    private String getBody(Request request) {
        String body;
        body = respondToRequest(request);
        return body;
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

        } else {

            response = new PongResponse();

        }

        return addStatusCode(response.contentType() + "\r\n\r\n" + response.body());
    }

    private boolean isFormRequest(Request request) {
        return request.headers().startsWith("GET /form");
    }

    private boolean isPostRequest(Request request) {
        return request.headers().startsWith("POST ");
    }

    private boolean isSleepRequest(Request request) {
        return request.headers().startsWith("GET /ping?sleep");
    }

    private boolean isResourceRequest(Request request) {
        return request.headers().startsWith("GET /browse");
    }

    private String addStatusCode(String body){
        return "HTTP/1.0 200 OK" + "\r\n" + body;
    }


}