package com.arlandis;

import com.arlandis.interfaces.*;

import java.io.*;

public class Server implements Responder {

    private final ResponseBuilder builder;
    private RequestFactory requestFactory;
    private NetworkIO networkIO;

    public Server(NetworkIO networkIO, RequestFactory requestFactory, ResponseBuilder builder) {
        this.networkIO = networkIO;
        this.requestFactory = requestFactory;
        this.builder = builder;

    }

    public void respond() {
        String response;
        Request request = null;

        try {
            request = requestFactory.nextRequest(networkIO);
        } catch (IOException e) {
            e.printStackTrace();
        }

        response = builder.generateResponse(request);
        networkIO.send(response);
    }

}
