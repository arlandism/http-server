package com.arlandis;

import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.RequestFactory;

import java.io.IOException;

public class HttpRequestFactory implements RequestFactory {

    public HttpRequest nextRequest(NetworkIO networkIO) throws IOException {

        String requestHeaders = readRequestHeaders(networkIO);
        HttpRequest request = buildRequest(networkIO, requestHeaders);

        return request;
    }

    private HttpRequest buildRequest(NetworkIO networkIO, String requestHeaders) throws IOException {
        HttpRequest request = new HttpRequest(requestHeaders);

        setBodyIfPresent(networkIO, request);
        return request;
    }

    private void setBodyIfPresent(NetworkIO networkIO, HttpRequest request) throws IOException {
        if (request.hasBody()) {

            setRequestBody(request, networkIO);

        }
    }

    private void setRequestBody(HttpRequest request, NetworkIO networkIO) throws IOException {
        char[] requestBody = networkIO.read(request.bytesToRead());
        String body = new String(requestBody);
        request.setBody(body);
    }

    private String readRequestHeaders(NetworkIO networkIO) throws IOException {
        StringBuilder requestHeaders = new StringBuilder();
        String nextHeader = "bar";
        while (!nextHeader.equals("")) {
            nextHeader = readNextHeader(networkIO, requestHeaders);
        }
        return requestHeaders.toString();
    }

    private String readNextHeader(NetworkIO networkIO, StringBuilder requestHeaders) throws IOException {
        String nextHeader;
        nextHeader = networkIO.readLine();
        requestHeaders.append(nextHeader);
        return nextHeader;
    }
}