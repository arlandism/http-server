package com.arlandis;

import com.arlandis.Responses.*;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.ResponseBuilder;

public class HttpResponseBuilder implements ResponseBuilder {

    private ResourceRetriever retriever;

    public HttpResponseBuilder(ResourceRetriever retriever) {
        this.retriever = retriever;
    }

    @Override
    public String generateResponse(Request request) {
        String body = getBody(request);
        return wrapBody(body);

    }

    private String wrapBody(String body) {
        String statusHeader = "HTTP/1.0 200 OK";
        String contentTypeHeader = "Content-type: text/html";
        return statusHeader + "\r\n" + contentTypeHeader + "\r\n\r\n" + body;
    }

    private String getBody(Request request) {
        String body;
        body = respondToRequest(request);
        return body;
    }

    private String respondToRequest(Request request) {
        String body;

        if (request.headers().startsWith("GET /form")) {

            body = new GetFormResponse().content();

        } else if (request.headers().startsWith("POST ")) {

            body = new PostFormResponse(request).content();

        } else if (request.headers().startsWith("GET /ping?sleep")) {

            body = new SleepResponse(request, new ThreadSleeper()).content();

        } else if (request.headers().startsWith("GET /browse")) {

            body = new FileResponse(request, retriever).content();

        } else {

            body = new PongResponse().content();

        }

        return body;
    }

}