package com.arlandis;

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

        if (isFormRequest(request)) {
            body = formBody();
        } else if (isPostRequest(request)) {
            body = formParams(request);
        } else if (isSleepRequest(request)) {
            ThreadSleeper sleeper = new ThreadSleeper();
            request.sleep(sleeper);
            body = pongBody();
        } else if (isFileRequest(request)) {
            body = fileBody(request);
        } else {
            body = pongBody();
        }
        return body;
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

    private String pongBody() {
        return addHeaderAndBodyTags("pong");
    }

    private String formParams(Request request) {
        String LINE_BREAK = "<br />";
        return "foo = " + decodeValue(request.fooValue()) + " " + LINE_BREAK + " bar = " + decodeValue(request.barValue());
    }

    private String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

}