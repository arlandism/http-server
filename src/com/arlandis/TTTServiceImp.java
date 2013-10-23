package com.arlandis;

import com.arlandis.Responses.TicTacToeService.Move;
import com.arlandis.interfaces.TTTService;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TTTServiceImp implements TTTService
{

    private Socket socket;

    public TTTServiceImp(Socket connSocket) {
        socket = connSocket;
    }

    @Override
    public String answer(Move[] queryItem) throws IOException {
        JsonObject toSend = new JsonObject();
        JsonObject moves = new JsonObject();

        Integer spot = queryItem[0].getPosition();
        String token = queryItem[0].getToken();
        moves.addProperty(spot.toString(), token);
        toSend.add("board", moves);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream());

        out.write(toSend.toString());
        out.close();
        return "";
    }
}
