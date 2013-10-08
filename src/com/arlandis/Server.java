package com.arlandis;

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
            Request request = new Request(rawRequestHeaders);

            if (request.hasBody()){
                char[] requestBody = readBody(in, request.bytesToRead());
                String stringVersion = new String(requestBody);
                Integer fooBegin = stringVersion.indexOf("=") + 1;
                Integer fooEnd = stringVersion.indexOf("&");
                Integer barBegin = stringVersion.indexOf("=", fooEnd) + 1;
                String barValue = stringVersion.substring(barBegin, stringVersion.length());
                String fooValue = stringVersion.substring(fooBegin, fooEnd);
                response = "foo = " + fooValue + "<br />bar = " + barValue;
            }  else {
                response = buildResponse(rawRequestHeaders);
            }

            out.print(response);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private String buildResponse(String requestHeaders){
        ResponseBuilder builder = new ResponseBuilder(requestHeaders);
        return builder.response();
    }
}
