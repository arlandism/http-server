import com.arlandis.*;
import com.arlandis.Responses.GetFormResponse;
import com.arlandis.Responses.PongResponse;
import com.arlandis.Responses.PostFormResponse;
import com.arlandis.Responses.SleepResponse;
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
    public void testSleepResponse(){
        MockSleeper mockSleeper = new MockSleeper();
        Sleeper sleeper = mockSleeper;
        mockFeatureParser.setSleepValue(true);
        Request sleepRequest = new MockRequest("GET /ping?sleep=5000 HTTP/1.0\r\n\r\n", "/ping?sleep=5000");
        String response = builder.generateResponse(sleepRequest, parser);
        SleepResponse sleepResponse = new SleepResponse(sleepRequest, sleeper);
        assertCorrectResponse(response, sleepResponse);
    }

    @Test
    public void testFormResponse(){
        mockFeatureParser.setForm(true);
        Request formRequest = new MockRequest("GET /form HTTP/1.0\r\n\r\n", "/form");
        String response = builder.generateResponse(formRequest, parser);
        GetFormResponse formResponse = new GetFormResponse();
        assertCorrectResponse(response, formResponse);
    }

    @Test
    public void testPongResponse(){
        mockFeatureParser.setPing(true);
        Request pingRequest = new MockRequest("GET /ping HTTP/1.0\r\n\r\n", "/ping");
        String response = builder.generateResponse(pingRequest, parser);
        PongResponse pongResponse = new PongResponse();
        assertCorrectResponse(response, pongResponse);
    }

    @Test
    public void testPostFormResponse(){
        mockFeatureParser.setPostForm(true);
        Request postFormRequest = new MockRequest("POST /form HTTP/1.0\r\n\r\n", "/form");
        postFormRequest.setBody("foo=hello&bar=backatya");
        String response = builder.generateResponse(postFormRequest, parser);
        PostFormResponse postFormResponse = new PostFormResponse(postFormRequest);
        assertCorrectResponse(response, postFormResponse);
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

    private void assertCorrectResponse(String response, Response formResponse) {
        assertEquals(response, "HTTP/1.0 200 OK" + "\r\n" + "Content-type: " + formResponse.contentType() + "\r\n\r\n" + formResponse.body());
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
