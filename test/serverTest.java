import org.junit.Test;
import com.arlandis.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class serverTest {

    @Test
    public void testServerResponseStatus(){
        try{

            Integer port = 8000;
            Server server = new Server(port);
            Socket clientSocket = new Socket("localhost", port);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.print("GET /ping HTTP/1.0\r\n\r\n");
            server.respond();
            String serverResponse = in.readLine();
            server.close();
            clientSocket.close();
            assertEquals("HTTP/1.0 200 OK", serverResponse);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testServerResponseContentType(){

        try{

            Integer port = 8100;
            Server server = new Server(port);
            Socket clientSocket = new Socket("localhost", port);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.print("GET /ping HTTP/1.0\r\n\r\n");
            server.respond();
            String serverResponse = "";
            String nextLine;
            while ((nextLine = in.readLine()) != null){
                serverResponse += nextLine;
            }
            server.close();
            clientSocket.close();
            assertTrue(serverResponse.contains("Content-type: text/html"));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
