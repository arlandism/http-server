package com.arlandis;

import com.arlandis.Responses.TicTacToeService.Move;
import com.arlandis.interfaces.NetworkIO;
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
    private NetworkIO networkIO;

    public TTTServiceImp(NetworkIO networkIO) {
        this.networkIO = networkIO;
    }

    @Override
    public String answer(Move[] queryItem) throws IOException {
        JsonObject moves = new JsonObject();
        for (Move move: queryItem){
            addMove(moves, move);
        }
        JsonObject gameData = addMovesToGameData(new JsonObject(), moves);
        networkIO.send(gameData.toString());
        return "";
    }

    private void addMove(JsonObject movesSoFar, Move move){
        Integer position = move.getPosition();
        String token = move.getToken();
        movesSoFar.addProperty(position.toString(), token);
    }

    private JsonObject addMovesToGameData(JsonObject gameData, JsonObject moves){
        gameData.add("board", moves);
        return gameData;
    }
}
