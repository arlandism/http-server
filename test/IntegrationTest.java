import com.arlandis.*;
import com.arlandis.FileReader;
import com.arlandis.interfaces.RequestFactory;
import com.arlandis.interfaces.TTTService;
import com.arlandis.interfaces.Toggler;
import mocks.MockService;
import mocks.MockToggler;
import org.junit.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import static junit.framework.Assert.assertEquals;
public class IntegrationTest {

    private Server server;
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private String filePath = "test/tmp/testing.txt";
    private String testFileContent = "Testing...123...Testing...4567";
    private File f;
    private BufferedWriter writer;

    @Before
    public void setUp(){
        try {
            Integer port = generateRandomPort();
            ServerSocket servSocket = new ServerSocket(port);
            client = trySocketCreation(port);
            NetworkIOImp networkIO = new NetworkIOImp(servSocket.accept());
            RequestFactory requestFactory = new HttpRequestFactory(networkIO);
            FileResponseFactoryImp responseFactoryImp = new FileResponseFactoryImp();
            FileReader retriever = new FileReader();
            TTTService service = new MockService("bar");
            HttpResponseBuilder builder = new HttpResponseBuilder(retriever, responseFactoryImp, service);
            Toggler toggler = new MockToggler(true);
            server = new Server(networkIO, requestFactory, builder, toggler);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            (new File("test/tmp")).mkdir();
            f = new File(filePath);
            f.createNewFile();
            writer = new BufferedWriter(new FileWriter(f.getAbsolutePath()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Integer generateRandomPort() {
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

    public static String readResponse(BufferedReader in) {
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
            f.delete();
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

    @Test
    public void testBrowse() throws IOException {
        Config.instance().setRootDir(System.getProperty("user.dir"));
        writer.write(testFileContent);
        writer.close();
        out.print("GET /browse/" + filePath + " HTTP/1.0");
        out.println("");
        out.println("");
        server.respond();
        String response = readResponse(in);
        String expected = "HTTP/1.0 200 OK" +
                          "Content-type: text/html" +
                           testFileContent;
        assertEquals(expected, response);
    }

}