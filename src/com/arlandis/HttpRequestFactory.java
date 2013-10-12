package com.arlandis;

import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.RequestFactory;

import java.io.IOException;

public class HttpRequestFactory implements RequestFactory {

    public HttpRequest nextRequest(NetworkIO networkIO) throws IOException {
        String nextHeader;
        StringBuilder requestHeaders = new StringBuilder();
        while (!(nextHeader = networkIO.readLine()).equals("")) {
            requestHeaders.append(nextHeader);
        }

        HttpRequest request = null;
        try {
            request = new HttpRequest(requestHeaders.toString());

            if (request.hasBody()) {
                char[] requestBody = networkIO.read(request.bytesToRead());

                String body = new String(requestBody);
                request.setBody(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;
    }
}