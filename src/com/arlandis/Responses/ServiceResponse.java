package com.arlandis.Responses;

import com.arlandis.Responses.TicTacToeService.Move;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.TTTService;

import java.io.IOException;

public class ServiceResponse {

    private TTTService service;
    private Request request;

    public ServiceResponse(TTTService service, Request request) {
        this.service = service;
        this.request = request;
    }

    public String contentType() {
        return "Content-type: application/json";
    }

    public String body() {
        String responseFromService;
        responseFromService = getServiceResponse();
        return responseFromService;
    }

    private String getServiceResponse() {
        String responseFromService;

        try {
            responseFromService = service.answer(parseRequestIntoMoves(), depthSection());
        } catch (IOException e) {
            e.printStackTrace();
            responseFromService = "NOT FOUND";
        }
        return responseFromService;
    }

    private Move[] parseRequestIntoMoves(){
        String[] moveStrings = splitQueryString(moveSection());
        Move[] moves = new Move[moveStrings.length];
        for (int i = 0; i < moveStrings.length; i++){
             moves[i] = moveFromString(moveStrings[i]);
        }
        return moves;
    }

    private String[] splitQueryString(String queryString){
        return queryString.split("&");
    }

    private Move moveFromString(String moveString){
        String positionAsString = moveString.substring(moveString.indexOf("=") - 1, moveString.indexOf("="));
        Integer position = Integer.parseInt(positionAsString);
        String token = moveString.substring(moveString.indexOf("=") + 1, moveString.indexOf("=") + 2);
        return new Move(position, token);
    }

    private String moveSection(){
        Integer queryStart = request.requestedResource().indexOf("?") + 1;
        Integer endIndex = request.requestedResource().indexOf("depth") - 1;
        return request.requestedResource().substring(queryStart, endIndex);
    }

    private String depthSection(){
        String queryString = request.requestedResource();
        Integer startOfParam = queryString.indexOf("depth=") + "depth=".length();
        return queryString.substring(startOfParam);
    }
}
