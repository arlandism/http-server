import com.arlandis.Server;
import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.RequestFactory;
import mocks.MockNetworkIO;
import mocks.MockRequest;
import mocks.MockRequestFactory;
import mocks.MockResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    private MockNetworkIO mockIO;
    private NetworkIO io;
    private Request mockRequest;
    private MockResponseBuilder builder;
    private RequestFactory factory;
    private Server server;

    @Before
    public void setUp(){
        mockIO = new MockNetworkIO();
        io = mockIO;
        mockRequest = new MockRequest("", "");
        builder = new MockResponseBuilder("baz");
        factory = new MockRequestFactory(mockRequest);
        server = new Server(io, factory, builder);
    }

    @Test
    public void testResponseFromBuilderSent(){
        server.respond();
        assertEquals("baz", mockIO.lastCallArg()) ;
    }

    @Test
    public void testBuilderGetsRequest(){
        server.respond();
        assertEquals(mockRequest, builder.history());
    }
}
