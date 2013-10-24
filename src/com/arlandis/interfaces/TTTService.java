package com.arlandis.interfaces;

import com.arlandis.Responses.TicTacToeService.Move;

import java.io.IOException;

public interface TTTService {

    String answer(Move[] movesToSend, String depth) throws IOException;

}
