import com.arlandis.Server;
import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.RequestFactory;
import com.arlandis.interfaces.Inventory;
import mocks.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {

    private MockNetworkIO mockIO;
    private NetworkIO io;
    private Request mockRequest;
    private MockResponseBuilder builder;
    private RequestFactory factory;
    private Inventory inventory;
    private Server server;

    @Before
    public void setUp(){
        mockIO = new MockNetworkIO();
        io = mockIO;
        mockRequest = new MockRequest("", "");
        builder = new MockResponseBuilder("baz");
        factory = new MockRequestFactory(mockRequest);
        inventory = new MockInventory(true);
        server = new Server(io, factory, builder, inventory);
    }

    @Test
    public void testResponseFromBuilderSent(){
        server.respond();
        assertEquals("baz", mockIO.lastCallArg()) ;
    }

    @Test
    public void testBuilderGetsRequest(){
        server.respond();
        assertTrue(builder.calledWith(mockRequest));
    }

    @Test
    public void testTogglerGetsPassedToBuilder(){
        server.respond();
        assertTrue(builder.calledWith(inventory));
    }
}
