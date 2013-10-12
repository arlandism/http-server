package com.arlandis;

import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.RequestFactory;
import com.arlandis.interfaces.ResponseBuilder;

import java.io.*;

public class Server {

    private final ResponseBuilder builder;
    private RequestFactory requestFactory;
    private NetworkIO networkIO;

    public Server(NetworkIO networkIO, RequestFactory requestFactory, ResponseBuilder builder){
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

        response = this.builder.generateResponse(request);
        //HttpResponseBuilder builder = new HttpResponseBuilder(request);
        //response = builder.response();
        networkIO.send(response);
    }

}
