package com.arlandis;

import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.RequestInterface;
import com.arlandis.interfaces.ResponseBuilderInterface;

import java.io.*;

public class Server {

    private NetworkIO networkIO;
    private ResponseBuilderInterface builder;

    public Server(NetworkIO networkIO, ResponseBuilderInterface builder){
        this.networkIO = networkIO;
        this.builder = builder;
    }

    public void respond() {
        String response;
        Request request = null;
        String rawRequestHeaders;

        try {
            rawRequestHeaders = readHeaders();

            request = getRequest(rawRequestHeaders);
        } catch (IOException e) {
            e.printStackTrace();
        }
            ResponseBuilder builder = new ResponseBuilder(request);
            response = builder.response();
            networkIO.send(response);
    }

    private Request getRequest(String rawRequestHeaders) throws IOException {
        Request request = new Request(rawRequestHeaders);

        if (request.hasBody()){
            char[] requestBody = readBody(request.bytesToRead());
            String body = new String(requestBody);
            request.setBody(body);
        }

        return request;
    }

    private char[] readBody(Integer bytesToRead) throws IOException {
        char [] responseBody;
        responseBody = networkIO.read(bytesToRead);
        return responseBody;
    }

    private String readHeaders() throws IOException {
        String nextHeader;
        StringBuilder requestHeaders = new StringBuilder();
        while (!(nextHeader = networkIO.readLine()).equals("")){
            requestHeaders.append(nextHeader);
        }
        return requestHeaders.toString();
    }

}
