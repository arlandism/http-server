import com.arlandis.Responses.TicTacToeService.Move;
import com.arlandis.TTTServiceImp;
import com.arlandis.interfaces.NetworkIO;
import com.google.gson.JsonObject;
import mocks.MockNetworkIO;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TTTServiceImpTest {
    private Move[] moves = {new Move(1, "x")};
    private JsonObject jsonMoves;
    private JsonObject expectedServiceQuery;
    private TTTServiceImp service;
    private MockNetworkIO mockIO = new MockNetworkIO();
    private NetworkIO networkIO = mockIO;

    @Before
    public void setUp() {
        jsonMoves = new JsonObject();
        jsonMoves.addProperty("1", "x");
        expectedServiceQuery = new JsonObject();
        service = new TTTServiceImp(networkIO);
    }

    @Test
    public void testServiceQueriedCorrectly() throws IOException {
        mockIO.addToOutputQueue(null);
        expectedServiceQuery.add("board", jsonMoves);
        service.answer(moves);
        String calledWith = mockIO.lastCallArg();
        assertEquals(expectedServiceQuery.toString(), calledWith);
    }

    @Test
    public void testResponseComesFromService() throws IOException {
       mockIO.addToOutputQueue("bar");
       mockIO.addToOutputQueue(null);
       String serviceResponse = service.answer(moves);
       assertEquals("bar",serviceResponse);
    }
}
