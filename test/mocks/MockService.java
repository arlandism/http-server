package mocks;

import com.arlandis.Responses.TicTacToeService.Move;
import com.arlandis.interfaces.TTTService;

public class MockService implements TTTService {

    private String answerToReturn;
    private Move[] movesCalledWith;
    private String difficultyCalledWith;

    public MockService(String answerToReturn) {
        this.answerToReturn = answerToReturn;
    }

    public Boolean calledWith(Move m) {
        Boolean found = false;
        for (Move move: movesCalledWith){
            if (movesAreEqual(move, m))
                found = true;
        }
        return found;
    }

    public boolean calledWith(String difficulty) {
       return difficultyCalledWith.equals(difficulty);
    }

    private Boolean movesAreEqual(Move moveOne, Move moveTwo){
       Integer moveOnePos = moveOne.getPosition();
       Integer moveTwoPos = moveTwo.getPosition();
       String moveOneToken = moveOne.getToken();
       String moveTwoToken = moveTwo.getToken();
       return moveOnePos.equals(moveTwoPos) && moveOneToken.equals(moveTwoToken);
    }

    @Override
    public String answer(Move[] movesToSend, String depth) {
        movesCalledWith = movesToSend;
        difficultyCalledWith = depth;
        return answerToReturn;
    }


}
