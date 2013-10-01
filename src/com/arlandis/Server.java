package com.arlandis;

import java.io.IOException;
import java.io.PrintWriter;
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

    public void close() {
    }

    public void respond() {
        try{
          Socket client = serverSocket.accept();
          PrintWriter out = new PrintWriter(client.getOutputStream(),true);
          out.println("HTTP/1.0 200 OK");
          out.println("Content-type: text/html");
          out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
