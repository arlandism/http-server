import com.arlandis.*;
import com.arlandis.interfaces.RequestFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntegrationTest {

    private Server server;
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;

    @Before
    public void setUp(){
        try {
            Integer port = generateRandomPort();
            ServerSocket servSocket = new ServerSocket(port);
            client = trySocketCreation(port);
            NetworkIOImp networkIO = new NetworkIOImp(servSocket.accept());
            RequestFactory factory = new HttpRequestFactory();
            HttpResponseBuilder builder = new HttpResponseBuilder();
            server = new Server(networkIO, factory, builder);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Integer generateRandomPort() {
        final int MIN_PORT = 2000;
        final int MAX_PORT = 65535;

        Random rand = new Random();
        return rand.nextInt((MAX_PORT - MIN_PORT) + 1) + MIN_PORT;
    }

    private Socket trySocketCreation(Integer port) {
        try {
            client = new Socket("localhost", port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }

    private String readResponse(BufferedReader in) {
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

    private void sendPingRequest(PrintWriter out) {
        out.print("GET /ping HTTP/1.0\r\n\r\n");
        out.println("");
    }

    @After
    public void tearDown() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGETIntegration() {
        sendPingRequest(out);
        server.respond();
        String response = readResponse(in);
        String expected = "HTTP/1.0 200 OK" +
                          "Content-type: text/html" +
                          "<html><body>pong</body></html>";
        assertEquals(expected, response);
    }

    @Test
    public void testPOSTPing() {
        out.print("POST /form HTTP/1.0\r\n");
        out.print("Content-Length: 17\r\n");
        out.println("");
        out.print("foo=fooba&bar=baz");
        out.println("");
        server.respond();
        String response = readResponse(in);
        String expected = "HTTP/1.0 200 OK" +
                          "Content-type: text/html" +
                          "foo = fooba " + "<br /> " +
                          "bar = baz";
        assertEquals(expected, response);
    }
}