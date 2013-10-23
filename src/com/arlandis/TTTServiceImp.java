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
    private NetworkIO io;

    public TTTServiceImp(NetworkIO networkIO) {
        io = networkIO;
    }

    @Override
    public String answer(Move[] queryItem) throws IOException {
        JsonObject moves = new JsonObject();
        for (Move move: queryItem){
            addMove(moves, move);
        }

        JsonObject gameData = addMovesToGameData(new JsonObject(), moves);
        io.send(gameData.toString());
        return "";
    }

    private JsonObject addMove(JsonObject movesSoFar, Move move){
        Integer position = move.getPosition();
        String token = move.getToken();
        movesSoFar.addProperty(position.toString(), token);
        return movesSoFar;
    }

    private JsonObject addMovesToGameData(JsonObject gameData, JsonObject moves){
        gameData.add("board", moves);
        return gameData;
    }

}
