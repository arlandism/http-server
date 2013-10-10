package com.arlandis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Start{

    public static void main(String[] args){

        Integer port = portNum(args);
        ServerSocket serverSock = null;
        ResponseBuilderInterface builder = new ResponseBuilder();

        try {
            serverSock = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Server starting on port: %d\n", port);

        while (true){
            try {
                final Socket connSocket = serverSock.accept();
                NetworkIOImp networkIO = new NetworkIOImp(connSocket);
                final Server server = new Server(networkIO, builder);
                new Thread(new Runnable() {{
                }
                    @Override
                    public void run() {
                        server.respond();
                    }
                }).start();

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
