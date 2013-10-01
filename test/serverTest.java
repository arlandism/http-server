import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.arlandis.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class serverTest {

    private Server server;
    private Random rand = new Random();
    private Integer port;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @Before
    public void setUp() throws IOException {
        port = rand.nextInt((6000 - 2000) + 1) + 2000;
        server = new Server(port);
        try{
          clientSocket = new Socket("localhost", port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @After
    public void tearDown(){
        server.close();
    }

    @Test
    public void testServerResponseStatus(){

        try{

            out.print("GET /ping HTTP/1.0\r\n\r\n");
            server.respond();
            String serverResponse = in.readLine();
            clientSocket.close();
            assertEquals("HTTP/1.0 200 OK", serverResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testServerResponseContentType(){

        try{

            out.print("GET /ping HTTP/1.0\r\n\r\n");
            server.respond();
            String serverResponse = "";
            String nextLine;
            while ((nextLine = in.readLine()) != null){
                serverResponse += nextLine;
            }
            clientSocket.close();
            assertTrue(serverResponse.contains("Content-type: text/html"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
