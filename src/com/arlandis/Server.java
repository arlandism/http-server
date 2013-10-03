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
            String formBody = "<html><body>" +
                              "<form method='post', action='/form'>" +
                              "<label><input name='foo'>foo</label>" +
                              "<label><input name='bar'>bar</label>";
            try{
                while (!(nextLine = in.readLine()).equals("")){
                   request += nextLine;
                }
            }catch (IOException e){
                    e.printStackTrace();
                }
            if (request.startsWith("GET /form")){

                String head = "HTTP/1.0 200 OK";
                String contentType = "Content-type: text/html";
                response = head + "\n" + contentType + "\n\r\n" + formBody;
            }
            else {
                response = buildResponse();
            }

            out.print(response);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildResponse(){
       String response;
       String head = "HTTP/1.0 200 OK";
       String contentType = "Content-type: text/html";
       String body = "<html><body>pong</body></html>";
       response = head + "\n" + contentType + "\n\r\n" + body;
       return response;
    }
}
