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
    private String depth = "20";

    @Before
    public void setUp() {
        jsonMoves = new JsonObject();
        jsonMoves.addProperty("1", "x");
        expectedServiceQuery = new JsonObject();
        service = new TTTServiceImp(networkIO);
        mockIO.addToOutputQueue("bar");
        mockIO.addToOutputQueue(null);
    }

    @Test
    public void testTTTQueriedCorrectly() throws IOException {
        expectedServiceQuery.add("board", jsonMoves);
        expectedServiceQuery.addProperty("depth", depth);
        service.answer(moves, depth);
        String sentFromService = mockIO.lastCallArg();
        assertEqualJson(expectedServiceQuery, sentFromService);
    }

    @Test
     public void testResponseComesFromIO() throws IOException {
        String serviceResponse = service.answer(moves, "bar");
        assertEquals("bar", serviceResponse);
    }

    private void assertEqualJson(JsonObject expectedJsonObj, String actualJsonString) {
        assertEquals(expectedJsonObj.toString(), actualJsonString);
    }


}
