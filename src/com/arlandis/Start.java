package com.arlandis;

import com.arlandis.interfaces.RequestFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Start {

    public static void main(String[] args) {

        Integer port = portNum(args);
        ServerSocket serverSock = null;
        RequestFactory factory = new HttpRequestFactory();
        HttpResponseBuilder builder = new HttpResponseBuilder();

        try {
            serverSock = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Server starting on port: %d\n", port);

        while (true) {

            try {
                final Socket connSocket = serverSock.accept();
                NetworkIOImp networkIO = new NetworkIOImp(connSocket);
                final Server server = new Server(networkIO, factory, builder);
                new Thread(new ServerThread(server)).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Integer portNum(String[] args) {

        return new CommandLineParser(args).portNum();
    }
}
