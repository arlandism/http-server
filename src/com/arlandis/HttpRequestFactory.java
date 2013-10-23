package com.arlandis;

import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.RequestFactory;

import java.io.IOException;

public class HttpRequestFactory implements RequestFactory {

    private NetworkIO networkIO;

    public HttpRequestFactory(NetworkIO networkIO){
        this.networkIO = networkIO;
    }

    public HttpRequest nextRequest() throws IOException {

        String requestHeaders = readRequestHeaders();
        HttpRequest request = buildRequest(requestHeaders);

        return request;
    }

    private HttpRequest buildRequest(String requestHeaders) throws IOException {
        HttpRequest request = new HttpRequest(requestHeaders);

        setBodyIfPresent(request);
        return request;
    }

    private void setBodyIfPresent(HttpRequest request) throws IOException {
        if (request.hasBody()) {

            setRequestBody(request);

        }
    }

    private void setRequestBody(HttpRequest request) throws IOException {
        char[] requestBody = networkIO.read(request.bytesToRead());
        String body = new String(requestBody);
        request.setBody(body);
    }

    private String readRequestHeaders() throws IOException {
        StringBuilder requestHeaders = new StringBuilder();
        String nextHeader = "bar";
        while (!nextHeader.equals("")) {
            nextHeader = readNextHeader(requestHeaders);
        }
        return requestHeaders.toString();
    }

    private String readNextHeader(StringBuilder requestHeaders) throws IOException {
        String nextHeader = networkIO.readLine();
        requestHeaders.append(nextHeader);
        return nextHeader;
    }
}