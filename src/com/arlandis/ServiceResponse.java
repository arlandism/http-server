package com.arlandis;

import com.arlandis.Responses.TicTacToeService.Move;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.TTTService;

public class ServiceResponse {

    private TTTService service;
    private Request request;

    public ServiceResponse(TTTService service, Request request) {
        this.service = service;
        this.request = request;
    }

    public String body() {
        return service.answer(parseRequestIntoMoves(request));
    }

    public String contentType() {
        return "Content-type: application/json";
    }

    private Move[] parseRequestIntoMoves(Request request){
        String queryString = request.requestedResource();
        Integer questionMark = queryString.indexOf("?");
        String gameData = queryString.substring(questionMark + 1);
        String[] parts = breakString(gameData);
        Move[] moves = new Move[parts.length];
        for (int i = 0; i < parts.length; i++){
             moves[i] = moveFromString(parts[i]);
        }
        return moves;
    }

    private String[] breakString(String toBreak){
        return toBreak.split("&");
    }

    private Move moveFromString(String moveString){
        String positionAsString = moveString.substring(moveString.indexOf("=") - 1, moveString.indexOf("="));
        Integer position = Integer.parseInt(positionAsString);
        String token = moveString.substring(moveString.indexOf("=") + 1, moveString.indexOf("=") + 2);
        return new Move(position, token);
    }
}
