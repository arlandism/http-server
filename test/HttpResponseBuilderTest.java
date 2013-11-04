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
    private TTTService service;
    private MockFeatureParser mockFeatureParser;
    private FeatureParser parser;

    @Before
    public void setUp() {
        txtFileRequest = new MockRequest("GET /browse/" + testFilePath + " HTTP/1.0\r\n\r\n");
        serviceRequest = new MockRequest("GET /game?1=x&depth=10", "/game?1=x&depth=10");
        mockFactory = new MockFileResponseFactory(new MockResponse("mock content type", "bar"));
        factory = mockFactory;
        mockReader = new MockFileReader("foo");
        service = new MockService("foo");
        retriever = mockReader;
        builder = new HttpResponseBuilder(mockReader, factory, service);
        mockFeatureParser = new MockFeatureParser();
        parser = mockFeatureParser;
    }

    @Test
    public void testDanceWithFileResponseFactory() {
        mockFeatureParser.setBrowseValue(true);
        mockFeatureParser.setGameValue(true);
        String response = builder.generateResponse(txtFileRequest, parser);
        assertContentTypeAndBodyMatch("mock content type", "bar", response);
        assertFileResponseFactoryCalledCorrectly(txtFileRequest, retriever);
    }

    @Test
    public void testDanceWithService() {
        mockFeatureParser.setGameValue(true);
        String response = builder.generateResponse(serviceRequest, parser);
        assertContentTypeAndBodyMatch("application/json", "foo", response);
    }

    @Test
    public void testResponseNotFoundForNonToggledFeatures() {
        mockFeatureParser.setBrowseValue(false);
        String response = builder.generateResponse(txtFileRequest, parser);
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
