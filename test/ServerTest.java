import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.arlandis.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class ServerTest {

    private Server server;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @Before
    public void setUp() throws IOException {
        Integer port = generateRandomPort();
        server = new Server(port);
        clientSocket = trySocketCreation(port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private Integer generateRandomPort() {
        final int MIN_PORT = 2000;
        final int MAX_PORT = 65535;

        Random rand = new Random();
        return rand.nextInt((MAX_PORT - MIN_PORT) + 1) + MIN_PORT;
    }

    private Socket trySocketCreation(Integer port) {
        try {
            clientSocket = new Socket("localhost", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientSocket;
    }

    private String readResponse() {
        String response = "";
        String nextLine;
        try {
            while ((nextLine = in.readLine()) != null) {
                response += nextLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }

    private void sendPingRequest(){
      out.print("GET /ping HTTP/1.0\r\n\r\n");
      out.println("");
    }

    @After
    public void tearDown() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGETIntegration() {
        sendPingRequest();
        server.respond();
        String response = readResponse();
        assertTrue(response.startsWith("HTTP/1.0 200 OK"));
        assertTrue(response.contains("Content-type: text/html"));
        assertTrue(response.contains("<html><body>pong</body></html>"));
    }

    @Test
    public void testPOSTPing(){
        out.print("POST /form HTTP/1.0\r\n");
        out.print("Content-Length: 17\r\n");
        out.println("");
        out.print("foo=fooba&bar=baz");
        out.println("");
        server.respond();
        String response = readResponse();
        assertTrue(response.contains("foo = fooba<br />bar = baz"));
    }

}
