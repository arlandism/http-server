import com.arlandis.NetworkIOImp;
import com.arlandis.Responses.TicTacToeService.Move;
import com.arlandis.TTTServiceImp;
import com.arlandis.interfaces.NetworkIO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class TTTServiceIntegrationTest {

    private ServerSocket server;
    private Socket clientSocket;
    private Socket connSocket;

    @Before
    public void setUp() throws IOException {
        Integer port = IntegrationTest.generateRandomPort();
        server = new ServerSocket(port);
        clientSocket = new Socket("localhost", port);
        connSocket = server.accept();
    }
    @After
    public void tearDown() throws IOException {
        server.close();
        clientSocket.close();
        connSocket.close();
    }

    @Test
    public void testServiceIntegration() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
        NetworkIO networkIO = new NetworkIOImp(clientSocket);
        TTTServiceImp service = new TTTServiceImp(networkIO);
        Move[] moves = new Move[1];
        moves[0] = new Move(1, "x");
        service.answer(moves);
        JsonObject jsonMoves = new JsonObject();
        jsonMoves.addProperty("1", "x");
        JsonObject expectedServiceQuery = new JsonObject();
        expectedServiceQuery.add("board", jsonMoves);
        String response  = IntegrationTest.readResponse(in);
        assertTrue(expectedServiceQuery.toString().equals(response));
    }

}
