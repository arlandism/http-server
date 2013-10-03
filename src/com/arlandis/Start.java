package com.arlandis;

public class Start {

    public static void main(String[] args){

        Integer port = portNum(args);
        Server server = new Server(port);
        System.out.printf("Server starting on port: %d\n", port);

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
