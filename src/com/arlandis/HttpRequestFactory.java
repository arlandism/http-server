package com.arlandis;

import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.RequestFactory;

import java.io.IOException;

public class HttpRequestFactory implements RequestFactory {

    private NetworkIO networkIO;

    public HttpRequest nextRequest(NetworkIO networkIO) throws IOException {
        setNetworkIO(networkIO);
        String requestHeaders = readRequestHeaders();
        HttpRequest request = new HttpRequest(requestHeaders);

        if (request.hasBody()) {

            setRequestBody(request);

        }

        return request;
    }

    private void setRequestBody(HttpRequest request) throws IOException {
        char[] requestBody = networkIO.read(request.bytesToRead());
        String body = new String(requestBody);
        request.setBody(body);
    }

    private String readRequestHeaders() throws IOException {
        StringBuilder requestHeaders = new StringBuilder();
        String nextHeader;
        while (!(nextHeader = networkIO.readLine()).equals("")) {
            requestHeaders.append(nextHeader);
        }
        return requestHeaders.toString();
    }

    private void setNetworkIO(NetworkIO networkIO) {
        this.networkIO = networkIO;
    }
}