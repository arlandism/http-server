package com.arlandis;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Start{

    public static void main(String[] args){

        Integer port = portNum(args);
        Server server = new Server(port);
        System.out.printf("Server starting on port: %d\n", port);
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        while (true){
            server.respond();
        }
    }

    private static Integer portNum(String[] args){

        Integer port;

        CommandLineParser parser = new CommandLineParser(args);
        port = parser.portNum();

        return port;
    }
}
