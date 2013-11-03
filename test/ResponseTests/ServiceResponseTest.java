package ResponseTests;

import com.arlandis.Responses.GameResponse;
import com.arlandis.interfaces.TTTService;
import mocks.MockService;
import com.arlandis.Responses.TicTacToeService.Move;
import com.arlandis.interfaces.Request;
import mocks.MockRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ServiceResponseTest {

    private GameResponse gameResponse;
    private MockService mockService;
    private TTTService service;
    private Request request;

    @Before
    public void setUp(){
        mockService = new MockService("service response");
        service = mockService;
        String queryString = "/game?1=x&3=o&depth=20";
        request = new MockRequest("GET " + queryString, queryString);
        gameResponse = new GameResponse(service, request);
    }

    @Test
    public void testServiceResponseContentType(){
        assertEquals("application/json", gameResponse.contentType());
    }

    @Test
    public void testServiceResponseBody(){
        assertEquals("service response", gameResponse.body());
    }

    @Test
    public void testServiceResponseQueriesServiceWithMoves(){
        Move[] moves = { new Move(1, "x"), new Move(3, "o") };
        gameResponse.body();
        assertServiceCalledWithMoves(moves);
    }

    @Test
    public void testServiceResponseQueriesServiceWithDifficulty(){
        String difficulty = "20";
        gameResponse.body();
        assertServiceCalledWithDifficulty(difficulty);
    }

    private void assertServiceCalledWithDifficulty(String difficulty) {
        assertTrue(mockService.calledWith(difficulty));
    }

    private void assertServiceCalledWithMoves(Move[] moves) {
        for (Move m: moves){
            assertTrue(mockService.calledWith(m));
        }
    }
}
