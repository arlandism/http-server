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

public class serverTest {

    private Server server;
    private Random rand = new Random();
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @Before
    public void setUp() throws IOException {
        Integer port = rand.nextInt((6000 - 2000) + 1) + 2000;
        server = new Server(port);
        clientSocket = trySocketCreation(port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private Socket trySocketCreation(Integer port) {
        try{
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
            while ((nextLine = in.readLine()) != null){
                    response += nextLine;
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }    @After
    public void tearDown(){
        server.close();
        tryClientClose();
    }

    private void tryClientClose() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testResponseStatus(){

        out.print("GET /ping HTTP/1.0\r\n\r\n");
        server.respond();
        String serverResponse = readResponse();
        assertTrue(serverResponse.startsWith("HTTP/1.0 200 OK"));
    }

    @Test
    public void testResponseContentType(){

        out.print("GET /ping HTTP/1.0\r\n\r\n");
        server.respond();
        String serverResponse = readResponse();
        tryClientClose();
        assertTrue(serverResponse.contains("Content-type: text/html"));
    }

}
