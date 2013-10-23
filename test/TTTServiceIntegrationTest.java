import com.arlandis.NetworkIOImp;
import com.arlandis.Responses.TicTacToeService.Move;
import com.arlandis.TTTServiceImp;
import com.google.gson.JsonObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class TTTServiceIntegrationTest {

    private ServerSocket server;
    private Socket clientSocket;
    private Socket connSocket;
    private Move[] moves = {new Move(1, "x")};
    private JsonObject jsonMoves;
    private JsonObject expectedServiceQuery;
    private BufferedReader in;
    private TTTServiceImp service;

    @Before
    public void setUp() throws IOException {
        Integer port = IntegrationTest.generateRandomPort();
        server = new ServerSocket(port);
        clientSocket = new Socket("localhost", port);
        connSocket = server.accept();
        jsonMoves = new JsonObject();
        jsonMoves.addProperty("1", "x");
        expectedServiceQuery = new JsonObject();
        in = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
        service = new TTTServiceImp(new NetworkIOImp(clientSocket));
    }
    @After
    public void tearDown() throws IOException {
        server.close();
        clientSocket.close();
        connSocket.close();
    }

    //@Test
    //@Ignore("Save for later")
    //public void testServiceIntegration() throws IOException {
    //    service.answer(moves);
    //    expectedServiceQuery.add("board", jsonMoves);
    //    String response  = IntegrationTest.readResponse(in);
    //    assertTrue(expectedServiceQuery.toString().equals(response));
    //}

}
