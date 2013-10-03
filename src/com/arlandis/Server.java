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
            try{
                while (!(nextLine = in.readLine()).equals("")){
                   request += nextLine;
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
        String response;
        String head = "HTTP/1.0 200 OK";
        String contentType = "Content-type: text/html";
        String body;
        String formBody = "<html><body>" +
                "<form method='post', action='/form'>" +
                "<label><input name='foo'>foo</label>" +
                "<label><input name='bar'>bar</label>";

        if (request.startsWith("GET /form")){
            body = formBody;
           }
        else{
            body = "<html><body>pong</body></html>";
        }

        response = head + "\n" + contentType + "\n\r\n" + body;
        return response;
    }
}
