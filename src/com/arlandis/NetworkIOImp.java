package com.arlandis;

import com.arlandis.interfaces.NetworkIO;

import java.io.*;
import java.net.Socket;

public class NetworkIOImp implements NetworkIO {

    private PrintWriter out;
    private BufferedReader in;
    private Socket conn;

    public NetworkIOImp(Socket connSocket) {
        setIOStreams(connSocket);
        conn = connSocket;
    }

    private void setIOStreams(Socket connSocket) {
        try {
            in = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            out = new PrintWriter(connSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void send(String response) {
        out.println(response);
        closeOutputStream();
    }

    private void closeOutputStream() {
        try {
            conn.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
