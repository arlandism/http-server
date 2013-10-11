package com.arlandis;

import com.arlandis.interfaces.NetworkIO;

import java.io.IOException;

public class RequestFactoryImp implements RequestFactory {

    public Request nextRequest(NetworkIO networkIO) throws IOException {
        String nextHeader;
        StringBuilder requestHeaders = new StringBuilder();
        while (!(nextHeader = networkIO.readLine()).equals("")) {
            requestHeaders.append(nextHeader);
        }

        Request request = null;
        try {
            request = new Request(requestHeaders.toString());

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