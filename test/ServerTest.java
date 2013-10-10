import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.RequestInterface;
import com.arlandis.Server;
import mocks.MockNetworkIO;
import mocks.MockRequest;
import mocks.MockResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ServerTest {

    private MockNetworkIO mockIO;
    private NetworkIO io;
    private RequestInterface mockRequest;
    private MockResponseBuilder builder;

    @Before
    public void setUp(){
        mockIO = new MockNetworkIO();
        io = mockIO;
        mockRequest = new MockRequest("");
        builder = new MockResponseBuilder();
    }

    @Test
    public void testServerResponse(){
        mockIO.addToRequestQueue(mockRequest);
        Server server = new Server(io, builder);
        server.respond();
        assertTrue(mockIO.lastResponse().equals("bar"));
    }
}
