package com.arlandis;

import com.arlandis.interfaces.NetworkIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkIOImp implements NetworkIO {

    private PrintWriter out;
    private BufferedReader in;

    public NetworkIOImp(Socket connSocket) {
        try {

            in = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            out = new PrintWriter(connSocket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(String response) {
        out.print(response);
        out.close();
    }

    @Override
    public String readLine() throws IOException {
        return in.readLine();
    }

    @Override
    public char[] read(Integer bytesToRead) throws IOException {
        char[] characterStorage = new char[bytesToRead];
        in.read(characterStorage, 0, bytesToRead);
        return characterStorage;
    }

    @Override
    public Request nextRequest() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
