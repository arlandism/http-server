package com.arlandis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Start {

    public static void main(String[] args) throws IOException {

        Integer port = portNum(args);
        Config.instance().setRootDir(rootDir(args));
        Integer servicePort = new CommandLineParser(args).servicePort();
        ServerSocket serverSock;
        HttpRequestFactory requestFactory;
        FileResponseFactoryImp fileResponseFactory = new FileResponseFactoryImp();
        serverSock = new ServerSocket(port);

        while (true) {

            Socket connSocket = serverSock.accept();
            Socket socketForService = new Socket("localhost", servicePort);
            TTTServiceImp service = new TTTServiceImp(new NetworkIOImp(socketForService));
            HttpResponseBuilder builder = new HttpResponseBuilder(new FileReader(), fileResponseFactory, service);
            NetworkIOImp networkIO = new NetworkIOImp(connSocket);
            requestFactory = new HttpRequestFactory(networkIO);
            Server server = new Server(networkIO, requestFactory, builder);

            (new Thread(new ServerThread(server))).start();
        }
    }

    private static Integer portNum(String[] args) {

        return new CommandLineParser(args).portNum();
    }

    private static String rootDir(String[] args) {

        return new CommandLineParser(args).browsePath();
    }
}
