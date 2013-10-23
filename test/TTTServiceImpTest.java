import com.arlandis.Responses.TicTacToeService.Move;
import com.arlandis.TTTServiceImp;
import com.arlandis.interfaces.NetworkIO;
import com.google.gson.JsonObject;
import junit.extensions.TestSetup;
import mocks.MockNetworkIO;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class TTTServiceImpTest {

    private MockNetworkIO mockIO = new MockNetworkIO();
    private NetworkIO networkIO = mockIO;
    private Move[] moves = {new Move(1, "x")};
    private TTTServiceImp service = new TTTServiceImp(networkIO);
    private JsonObject jsonMoves = new JsonObject();
    private JsonObject expectedServiceQuery = new JsonObject();

    @Before
    public void setUp(){
        jsonMoves.addProperty("1", "x");
    }

    @Test
    public void testServiceImpSendsDataCorrectly() throws IOException {
        service.answer(moves);
        expectedServiceQuery.add("board", jsonMoves);
        assertTrue(mockIO.lastCallArg().equals(expectedServiceQuery.toString()));
    }
}
