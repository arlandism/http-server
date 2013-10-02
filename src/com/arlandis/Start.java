package com.arlandis;

import java.util.Scanner;

public class Start {

    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        boolean terminated = false;
        Integer port = portNum(args);
        Server server = new Server(port);
        System.out.printf("Server starting on port: %d\n", port);

        while (!terminated){
            server.respond();
            terminated = !(in.hasNext());
        }
    }

    private static Integer portNum(String[] args){

        Integer port;

        CommandLineParser parser = new CommandLineParser(args);
        port = parser.portNum();

        return port;
    }
}
