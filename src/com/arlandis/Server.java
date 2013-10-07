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
            String request = "";
            String response;
            String nextLine;
            Integer numBytes;
            String requestBody;
            RequestParser parser;
            char[] requestBodyChars;
            try{
                while (!(nextLine = in.readLine()).equals("")){
                   request += nextLine;
                }

                if (request.contains("Content-Length:")){
                  parser = new RequestParser(request);
                  numBytes = parser.bytesToRead();
                  Integer max = numBytes + 6;
                  requestBodyChars = new char[max];
                  in.read(requestBodyChars, 0, max);
                  requestBody = new String(requestBodyChars);
                  out.print(requestBody);
                  out.close();
                }

            }catch (IOException e){
                    e.printStackTrace();
                }

            response = buildResponse(request);

            out.print(response);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildResponse(String request){
        ResponseBuilder builder = new ResponseBuilder(request);
        return builder.response();
    }
}
