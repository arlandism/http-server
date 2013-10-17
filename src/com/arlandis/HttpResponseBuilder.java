package com.arlandis;

import com.arlandis.Responses.PongResponse;
import com.arlandis.Responses.PostFormResponse;
import com.arlandis.Responses.SleepResponse;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.ResponseBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
            body = formBody();
        } else if (request.headers().startsWith("POST ")) {
           // body = formParams(request);
            body = new PostFormResponse(request).content();
        } else if (request.headers().startsWith("GET /ping?sleep")) {
            body = new SleepResponse(request, new ThreadSleeper()).content();
        } else if (isFileRequest(request)) {
            body = fileBody(request);
        } else {
            body = new PongResponse().content();
        }
        return body;
    }

    private boolean isFileRequest(Request request) {
        return request.headers().startsWith("GET /browse");
    }

    private String fileBody(Request request) {
        Integer startOfFilePath = request.headers().indexOf("browse") + 7;
        Integer endOfFilePath = request.headers().indexOf("HTTP");
        String filePath = request.headers().substring(startOfFilePath, endOfFilePath);
        String data;
        data = addHeaderAndBodyTags(this.retriever.retrieve(filePath.trim()));
        return data;
    }

    private String formBody() {

        return addHeaderAndBodyTags("<form method='post', action='/form'>" +
                "<label>foo<input name='foo'></label>" +
                "<br /><label>bar<input name='bar'></label>" +
                "<br /><input value='submit' type='submit'></form>");
    }

    private String addHeaderAndBodyTags(String content) {
        return "<html><body>" + content + "</body></html>";
    }

}