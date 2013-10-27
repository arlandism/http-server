package com.arlandis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Start {

    private static Integer port;
    private static Integer tttServicePort;
    private static Socket tttServiceSocket;
    private static TTTServiceImp tttServiceImp;
    private static ServerSocket serverSock;
    private static Socket connSocket;
    private static NetworkIOImp clientIO;
    private static HttpRequestFactory requestFactory;
    private static HttpResponseBuilder responseBuilder;
    private static FileResponseFactoryImp fileResponseFactory = new FileResponseFactoryImp();
    private static Server server;

    public static void main(String[] args) throws IOException {
        Config.instance().setRootDir(rootDir(args));

        port = portNum(args);
        tttServicePort = tttServicePort(args);
        serverSock = new ServerSocket(port);

        while (true) {

            connSocket = serverSock.accept();
            tttServiceSocket = new Socket("localhost", tttServicePort);
            tttServiceImp= new TTTServiceImp(new NetworkIOImp(tttServiceSocket));
            responseBuilder = new HttpResponseBuilder(new FileReader(), fileResponseFactory, tttServiceImp);
            clientIO = new NetworkIOImp(connSocket);
            requestFactory = new HttpRequestFactory(clientIO);
            server = new Server(clientIO, requestFactory, responseBuilder);

            (new Thread(new ServerThread(server))).start();
        }
    }

    private static int tttServicePort(String[] args) {
        return new CommandLineParser(args).servicePort();
    }

    private static Integer portNum(String[] args) {

        return new CommandLineParser(args).portNum();
    }

    private static String rootDir(String[] args) {

        return new CommandLineParser(args).browsePath();
    }
}
