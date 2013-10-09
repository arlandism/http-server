package com.arlandis;

import com.Sleeper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server (Integer port) {
      try{
          serverSocket = new ServerSocket(port);
      } catch (IOException e) {
          e.printStackTrace();
      }
    }

    public void respond() {
        try{
            Socket client = serverSocket.accept();
            PrintWriter out = new PrintWriter(client.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String response;
            String rawRequestHeaders = readHeaders(in);
            Request request = getRequest(in, rawRequestHeaders);

            response = buildResponse(request);
            out.print(response);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request getRequest(BufferedReader in, String rawRequestHeaders) throws IOException {
        Request request = new Request(rawRequestHeaders);

        if (request.hasBody()){
            char[] requestBody = readBody(in, request.bytesToRead());
            String body = new String(requestBody);
            request.setBody(body);
        }
        return request;
    }

    private char[] readBody(BufferedReader in, Integer bytesToRead) throws IOException {
        char [] responseBody = new char[bytesToRead];
        in.read(responseBody, 0, bytesToRead);
        return responseBody;
    }

    private String readHeaders(BufferedReader in) throws IOException {
        String nextHeader;
        StringBuilder requestHeaders = new StringBuilder();
        while (!(nextHeader = in.readLine()).equals("")){
            requestHeaders.append(nextHeader);
        }
        return requestHeaders.toString();
    }

    private String buildResponse(RequestInterface request){
        ResponseBuilder builder = new ResponseBuilder(request);
        return builder.response();
    }
}
