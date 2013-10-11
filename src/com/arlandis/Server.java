package com.arlandis;

import com.arlandis.interfaces.NetworkIO;

import java.io.*;

public class Server {

    private RequestFactory requestFactory;
    private NetworkIO networkIO;

    public Server(NetworkIO networkIO, RequestFactory requestFactory){
        this.networkIO = networkIO;
        this.requestFactory = requestFactory;
    }

    public void respond() {
        String response;
        Request request = null;

        try {
            request = requestFactory.nextRequest(networkIO);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResponseBuilder builder = new ResponseBuilder(request);
        response = builder.response();
        networkIO.send(response);
    }

}
