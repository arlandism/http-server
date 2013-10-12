package com.arlandis;

import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.RequestFactory;

import java.io.IOException;

public class HttpRequestFactory implements RequestFactory {

    public HttpRequest nextRequest(NetworkIO networkIO) throws IOException {

        String requestHeaders = readRequestHeaders(networkIO);

        HttpRequest request = null;
        try {
            request = new HttpRequest(requestHeaders);

            if (request.hasBody()) {
                setRequestBody(networkIO, request);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;
    }

    private void setRequestBody(NetworkIO networkIO, HttpRequest request) throws IOException {
        char[] requestBody = networkIO.read(request.bytesToRead());

        String body = new String(requestBody);
        request.setBody(body);
    }

    private String readRequestHeaders(NetworkIO networkIO) throws IOException {
        StringBuilder requestHeaders = new StringBuilder();
        String nextHeader;
        while (!(nextHeader = networkIO.readLine()).equals("")) {
            requestHeaders.append(nextHeader);
        }
        return requestHeaders.toString();
    }
}