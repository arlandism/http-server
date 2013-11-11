import com.arlandis.Server;
import com.arlandis.interfaces.*;
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
    private MockLogger mockLogger;
    private RequestFactory factory;
    private FeatureParser parser;
    private Server server;

    @Before
    public void setUp(){
        mockIO = new MockNetworkIO();
        io = mockIO;
        mockRequest = new MockRequest("GET /foo/bar HTTP/1.0\r\n\r\n", "");
        builder = new MockResponseBuilder("baz");
        factory = new MockRequestFactory(mockRequest);
        parser = new MockFeatureParser();
        mockLogger = new MockLogger();
        Logger logger = mockLogger;
        server = new Server(io, factory, builder, parser, logger);
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
    public void testParserGetsPassedToBuilder(){
        server.respond();
        assertTrue(builder.calledWith(parser));
    }

    @Test
    public void testLoggerGetsRequestAndResponse(){
        String response = "baz";
        String requestHeaders = "GET /foo/bar HTTP/1.0\r\n\r\n";
        server.respond();
        assertTrue(mockLogger.calledWith(requestHeaders + "\n" + response));
    }
}
