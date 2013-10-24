package com.arlandis.Responses.TicTacToeService;

public class Move {

    Integer position;
    String token;

    public Move(int position, String token) {
         this.position = position;
         this.token = token;
    }

    public Integer getPosition() {
        return position;
    }

    public String getToken() {
        return token;
    }
}
