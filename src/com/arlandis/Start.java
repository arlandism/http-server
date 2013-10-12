package com.arlandis;

import com.arlandis.interfaces.RequestFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Start{

    public static void main(String[] args){

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

        while (true){
            try {
                System.out.println("top of loop");
                final Socket connSocket = serverSock.accept();
                System.out.println("accepted");
                NetworkIOImp networkIO = new NetworkIOImp(connSocket);
                final Server server = new Server(networkIO, factory, builder);
                System.out.println("Thread count: " + Thread.activeCount());

                System.out.println("kicking off");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("pre generateResponse");
                        server.respond();
                        System.out.println("after generateResponse");
                    }
                }).start();
                System.out.println("DONE, KICKED off");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Integer portNum(String[] args){

        Integer port;

        CommandLineParser parser = new CommandLineParser(args);
        port = parser.portNum();

        return port;
    }
}
