package com.arlandis;

import com.arlandis.interfaces.*;

import java.io.*;

public class Server implements Responder {

    private final ResponseBuilder builder;
    private RequestFactory requestFactory;
    private NetworkIO networkIO;
    private FeatureParser parser;
    private Logger logger;
    private String response;
    private Request request;

    public Server(NetworkIO networkIO, RequestFactory requestFactory,
                  ResponseBuilder builder, FeatureParser parser,
                  Logger logger) {
        this.networkIO = networkIO;
        this.requestFactory = requestFactory;
        this.builder = builder;
        this.parser = parser;
        this.logger = logger;
    }

    public void respond() {
        request = nextRequest();
        response = builder.generateResponse(request, parser);
        logger.log(request.headers() + "\n" + response);
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
