import com.arlandis.*;
import com.arlandis.Responses.*;
import com.arlandis.interfaces.*;
import mocks.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpResponseBuilderTest {

    private MockRequest txtFileRequest;
    private MockRequest serviceRequest;
    private MockResponse fileResponse;
    private HttpResponseBuilder builder;
    private String testFilePath = "foo/bar/baz.txt";
    private MockFileResponseFactory mockFactory;
    private MockFileReader mockReader;
    private MockSleeper mockSleeper;
    private FileResponseFactory factory;
    private ResourceRetriever retriever;
    private TTTService service;
    private Sleeper sleeper;
    private MockFeatureParser mockFeatureParser;
    private FeatureParser parser;

    @Before
    public void setUp() {
        txtFileRequest = new MockRequest("GET /browse/" + testFilePath + " HTTP/1.0\r\n\r\n");
        serviceRequest = new MockRequest("GET /game?1=x&depth=10", "/game?1=x&depth=10");
        fileResponse = new MockResponse("mock content type", "bar");
        mockFactory = new MockFileResponseFactory(fileResponse);
        mockReader = new MockFileReader("foo");
        service = new MockService("foo");
        mockFeatureParser = new MockFeatureParser();
        mockSleeper = new MockSleeper();
        retriever = mockReader;
        factory = mockFactory;
        sleeper = mockSleeper;
        parser = mockFeatureParser;
        builder = new HttpResponseBuilder(mockReader, factory, service, sleeper);
        mockFeatureParser.setBrowseValue(true);
        mockFeatureParser.setPostForm(true);
        mockFeatureParser.setPing(true);
        mockFeatureParser.setSleepValue(true);
        mockFeatureParser.setForm(true);
        mockFeatureParser.setGameValue(true);
    }

    @Test
    public void testSleepResponse(){
        Request sleepRequest = new MockRequest("GET /ping?sleep=5000 HTTP/1.0\r\n\r\n", "/ping?sleep=5000");
        String response = builder.generateResponse(sleepRequest, parser);
        SleepResponse sleepResponse = new SleepResponse(sleepRequest, new MockSleeper());
        assertCorrectResponse(response, sleepResponse);
        assertTrue(mockSleeper.sleepCalledWith(5000));
    }

    @Test
    public void testFormResponse(){
        Request formRequest = new MockRequest("GET /form HTTP/1.0\r\n\r\n", "/form");
        String response = builder.generateResponse(formRequest, parser);
        GetFormResponse formResponse = new GetFormResponse();
        assertCorrectResponse(response, formResponse);
    }

    @Test
    public void testPongResponse(){
        Request pingRequest = new MockRequest("GET /ping HTTP/1.0\r\n\r\n", "/ping");
        String response = builder.generateResponse(pingRequest, parser);
        PongResponse pongResponse = new PongResponse();
        assertCorrectResponse(response, pongResponse);
    }

    @Test
    public void testPostFormResponse(){
        Request postFormRequest = new MockRequest("POST /form HTTP/1.0\r\n\r\n", "/form");
        postFormRequest.setBody("foo=hello&bar=backatya");
        String response = builder.generateResponse(postFormRequest, parser);
        PostFormResponse postFormResponse = new PostFormResponse(postFormRequest);
        assertCorrectResponse(response, postFormResponse);
    }

    @Test
    public void testDanceWithFileResponseFactory() {
        String response = builder.generateResponse(txtFileRequest, parser);
        assertCorrectResponse(response, fileResponse);
        assertEquals(txtFileRequest, mockFactory.history()[0]);
        assertEquals(retriever, mockFactory.history()[1]);
    }

    @Test
    public void testDanceWithService() {
        String response = builder.generateResponse(serviceRequest, parser);
        GameResponse gameResponse = new GameResponse(service, serviceRequest);
        assertCorrectResponse(response, gameResponse);
    }

    @Test
    public void testResponseNotFoundForNonToggledFeatures() {
        mockFeatureParser.setBrowseValue(false);
        String response = builder.generateResponse(txtFileRequest, parser);
        FeatureNotFoundResponse notFoundResponse = new FeatureNotFoundResponse();
        assertCorrectResponse(response, notFoundResponse);
    }

    private void assertCorrectResponse(String responseString, Response response) {
        assertEquals(responseString, "HTTP/1.0 200 OK" + "\r\n" + "Content-type: " + response.contentType() + "\r\n\r\n" + response.body());
    }
}
