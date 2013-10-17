package com.arlandis;

import com.arlandis.Responses.*;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.Response;
import com.arlandis.interfaces.ResponseBuilder;

public class HttpResponseBuilder implements ResponseBuilder {

    private ResourceRetriever retriever;

    public HttpResponseBuilder(ResourceRetriever retriever) {
        this.retriever = retriever;
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

        } else if (isFileRequest(request)) {

             response = FileResponseFactory.fileResponse(request, retriever);

        } else {

            response = new PongResponse();

        }

        return "HTTP/1.0 200 OK" + "\r\n" + response.contentType() + "\r\n\r\n" + response.body();
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

    private boolean isFileRequest(Request request) {
        return request.headers().startsWith("GET /browse");
    }

}