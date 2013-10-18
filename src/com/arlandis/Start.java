package com.arlandis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Start {

    public static void main(String[] args) {

        Integer port = portNum(args);
        ServerSocket serverSock = null;
        HttpRequestFactory requestFactory = new HttpRequestFactory();
        FileResponseFactoryImp fileResponseFactory = new FileResponseFactoryImp();
        HttpResponseBuilder builder = new HttpResponseBuilder(new FileReader(), fileResponseFactory);

        try {
            serverSock = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Server starting on port: %d\n", port);

        while (true) {

            try {
                Socket connSocket = serverSock.accept();
                NetworkIOImp networkIO = new NetworkIOImp(connSocket);
                Server server = new Server(networkIO, requestFactory, builder);
                (new Thread(new ServerThread(server))).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Integer portNum(String[] args) {

        return new CommandLineParser(args).portNum();
    }

    private static String rootDir(String[] args){

        return new CommandLineParser(args).browsePath();
    }

}
