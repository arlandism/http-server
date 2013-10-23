package com.arlandis;

import com.arlandis.Responses.TicTacToeService.Move;
import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.TTTService;
import com.google.gson.JsonObject;

import java.io.IOException;

public class TTTServiceImp implements TTTService
{
    private NetworkIO networkIO;

    public TTTServiceImp(NetworkIO networkIO) {
        this.networkIO = networkIO;
    }

    @Override
    public String answer(Move[] queryItem) throws IOException {
        JsonObject moves = movesToJson(queryItem);
        JsonObject gameData = addMovesToGameData(new JsonObject(), moves);
        networkIO.send(gameData.toString());
        return serviceData();
    }

    private JsonObject movesToJson(Move[] queryItem) {
        JsonObject moves = new JsonObject();

        for (Move move: queryItem){
            addMove(moves, move);
        }
        return moves;
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

    private String serviceData() throws IOException {
        String nextPiece;
        StringBuilder serviceData = new StringBuilder();
        while ((nextPiece = networkIO.readLine()) != null){
            serviceData.append(nextPiece);
        }
        return serviceData.toString();
    }

}
