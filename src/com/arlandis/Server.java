package com.arlandis;

import com.arlandis.interfaces.*;

import java.io.*;

public class Server implements Responder {

    private final ResponseBuilder builder;
    private RequestFactory requestFactory;
    private NetworkIO networkIO;
    private Toggler toggler;
    private String response;
    private Request request;

    public Server(NetworkIO networkIO, RequestFactory requestFactory, ResponseBuilder builder, Toggler toggler) {
        this.networkIO = networkIO;
        this.requestFactory = requestFactory;
        this.builder = builder;
        this.toggler = toggler;

    }

    public void respond() {
        request = nextRequest();
        response = builder.generateResponse(request, toggler);
        networkIO.send(response);
    }

    private Request nextRequest() {
        Request request = null;
        try {
            request = requestFactory.nextRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }

}
