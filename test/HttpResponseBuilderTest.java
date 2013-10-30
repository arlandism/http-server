import com.arlandis.*;
import com.arlandis.interfaces.*;
import mocks.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpResponseBuilderTest {

    private MockRequest pingRequest;
    private MockRequest formRequest;
    private MockRequest postRequest;
    private MockRequest sleepRequest;
    private MockRequest txtFileRequest;
    private MockRequest serviceRequest;
    private MockPostRequest encodedPostRequest;
    private HttpResponseBuilder builder;
    private String testFilePath = "foo/bar/baz.txt";
    private FileResponseFactory factory;
    private MockFileResponseFactory mockFactory;
    private MockFileReader mockReader;
    private ResourceRetriever retriever;
    private TTTService service;
    private String htmlContentType = "text/html";

    @Before
    public void setUp(){
        pingRequest = new MockRequest("GET /ping HTTP/1.0\r\n\r\n");
        formRequest = new MockRequest("GET /form HTTP/1.0\r\n\r\n");
        sleepRequest = new MockRequest("GET /ping?sleep=4 HTTP/1.0\r\n\r\n");
        txtFileRequest = new MockRequest("GET /browse/" + testFilePath +  " HTTP/1.0\r\n\r\n");
        serviceRequest = new MockRequest("GET /game?1=x&depth=10", "/game?1=x&depth=10");
        mockFactory = new MockFileResponseFactory(new MockResponse("mock content type", "bar"));
        factory = mockFactory;
        mockReader = new MockFileReader("foo");
        service = new MockService("foo");
        retriever = mockReader;
        builder = new HttpResponseBuilder(mockReader, factory, service);
    }

    @Test
    public void testGetPingResponse(){
        String expectedBody = "<html><body>pong</body></html>";

        String response = builder.generateResponse(pingRequest);
        assertContentTypeAndBodyMatch(htmlContentType, expectedBody, response);
    }

    @Test
    public void testGetFormResponse(){
        String expectedBody = "<html><body>" +
                              "<form method='post', action='/form'>" +
                              "<label>foo<input name='foo'></label>" +
                              "<br /><label>bar<input name='bar'></label>" +
                              "<br /><input value='submit' type='submit'>" +
                              "</form></body></html>";
        String response = builder.generateResponse(formRequest);
        assertContentTypeAndBodyMatch(htmlContentType, expectedBody, response);
    }

    @Test
    public void testSleepResponse(){
        String expectedBody = "<html><body>pong</body></html>";
        String response = builder.generateResponse(sleepRequest);
        assertContentTypeAndBodyMatch(htmlContentType, expectedBody, response);
    }

    @Test
    public void testFeatureNotFoundResponse(){
        MockRequest notFoundRequest = new MockRequest("GET /foobar HTTP/1.0\r\n\r\n");
        String expectedBody = "<html><body>The feature you're looking for can't be found.</body></html>";
        String response = builder.generateResponse(notFoundRequest);
        assertContentTypeAndBodyMatch("text/html", expectedBody, response);
    }

    @Test
    public void testDanceWithFileResponseFactory(){
        assertContentTypeAndBodyMatch("mock content type", "bar", builder.generateResponse(txtFileRequest));
        assertFileResponseFactoryCalledCorrectly(txtFileRequest, retriever);
    }

    @Test
    public void testDanceWithService(){
        HttpResponseBuilder builder = new HttpResponseBuilder(retriever, factory, service);
        assertContentTypeAndBodyMatch("application/json", "foo", builder.generateResponse(serviceRequest));
    }

    private void assertFileResponseFactoryCalledCorrectly(Request request, ResourceRetriever retriever) {
        assertEquals(request, mockFactory.history()[0]);
        assertEquals(retriever, mockFactory.history()[1]);
    }

    private void assertContentTypeAndBodyMatch(String expectedContentType, String expectedBody, String actual){
        String OK_STATUS = "HTTP/1.0 200 OK";
        String contentHeader = "Content-type: ";
        assertEquals(OK_STATUS + "\r\n" + contentHeader + expectedContentType + "\r\n\r\n" + expectedBody, actual);
    }
}
