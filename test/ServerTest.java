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

    @Before
    public void setUp(){
        mockIO = new MockNetworkIO();
        io = mockIO;
        mockRequest = new MockRequest("", "");
        builder = new MockResponseBuilder("baz");
        factory = new MockRequestFactory(mockRequest);
    }

    @Test
    public void getsRequestAndSendsToResponse(){
        Server server = new Server(io, factory, builder);
        server.respond();
        assertEquals("baz", mockIO.lastResponse()) ;
    }

    @Test
    public void getsResponseUsingRequest(){
        Server server = new Server(io, factory, builder);
        server.respond();
        assertEquals(mockRequest, builder.history());
    }
}
