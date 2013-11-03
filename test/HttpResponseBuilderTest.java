import com.arlandis.*;
import com.arlandis.interfaces.*;
import mocks.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpResponseBuilderTest {

    private MockRequest txtFileRequest;
    private MockRequest serviceRequest;
    private HttpResponseBuilder builder;
    private String testFilePath = "foo/bar/baz.txt";
    private FileResponseFactory factory;
    private MockFileResponseFactory mockFactory;
    private MockFileReader mockReader;
    private ResourceRetriever retriever;
    private Inventory inventory;
    private TTTService service;

    @Before
    public void setUp() {
        txtFileRequest = new MockRequest("GET /browse/" + testFilePath + " HTTP/1.0\r\n\r\n");
        serviceRequest = new MockRequest("GET /game?1=x&depth=10", "/game?1=x&depth=10");
        mockFactory = new MockFileResponseFactory(new MockResponse("mock content type", "bar"));
        factory = mockFactory;
        mockReader = new MockFileReader("foo");
        service = new MockService("foo");
        retriever = mockReader;
        inventory = new MockInventory(true);
        builder = new HttpResponseBuilder(mockReader, factory, service);
    }

    @Test
    public void testDanceWithFileResponseFactory() {
        assertContentTypeAndBodyMatch("mock content type", "bar", builder.generateResponse(txtFileRequest, inventory));
        assertFileResponseFactoryCalledCorrectly(txtFileRequest, retriever);
    }

    @Test
    public void testDanceWithService() {
        HttpResponseBuilder builder = new HttpResponseBuilder(retriever, factory, service);
        assertContentTypeAndBodyMatch("application/json", "foo", builder.generateResponse(serviceRequest, inventory));
    }

    @Test
    public void testResponseNotFoundForUnrecognizedRoutes() {
        String response = builder.generateResponse(new MockRequest("GET /foo HTTP/1.0\r\n\r\n"), inventory);
        assertContentTypeAndBodyMatch("text/html",
                "<html><body>The feature you're looking for can't be found.</body></html>", response);
    }

    @Test
    public void testBuilderAsksTogglerAboutRequest() {
        Boolean featureEnabled = true;
        MockInventory mock = new MockInventory(featureEnabled);
        Inventory inventory = mock;
        builder.generateResponse(txtFileRequest, inventory);
        assertTrue(mock.calledWith(txtFileRequest.headers()));
    }

    @Test
    public void testResponseNotFoundForNonToggledFeatures() {
        Boolean featureEnabled = false;
        MockInventory mock = new MockInventory(featureEnabled);
        Inventory inventory = mock;
        String response = builder.generateResponse(txtFileRequest, inventory);
        assertContentTypeAndBodyMatch("text/html",
                "<html><body>The feature you're looking for can't be found.</body></html>", response);
    }

    private void assertFileResponseFactoryCalledCorrectly(Request request, ResourceRetriever retriever) {
        assertEquals(request, mockFactory.history()[0]);
        assertEquals(retriever, mockFactory.history()[1]);
    }

    private void assertContentTypeAndBodyMatch(String expectedContentType, String expectedBody, String actual) {
        String OK_STATUS = "HTTP/1.0 200 OK";
        String contentHeader = "Content-type: ";
        assertEquals(OK_STATUS + "\r\n" + contentHeader + expectedContentType + "\r\n\r\n" + expectedBody, actual);
    }
}
