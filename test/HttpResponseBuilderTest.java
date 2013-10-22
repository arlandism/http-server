import com.arlandis.*;
import com.arlandis.interfaces.*;
import mocks.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpResponseBuilderTest {

    private MockRequest pingRequest;
    private MockRequest formRequest;
    private MockRequest postRequest;
    private MockRequest sleepRequest;
    private MockRequest txtFileRequest;
    private MockRequest directoryRequest;
    private MockPostRequest encodedPostRequest;
    private HttpResponseBuilder builder;
    private String testFilePath = "foo/bar/baz.txt";
    private FileResponseFactory factory;
    private MockFileResponseFactory mockFactory;
    private MockFileReader mockReader;
    private ResourceRetriever retriever;
    private String htmlContentType = "text/html";

    @Before
    public void setUp(){
        pingRequest = new MockRequest("GET /ping HTTP/1.0\r\n\r\n");
        formRequest = new MockRequest("GET /form HTTP/1.0\r\n\r\n");
        postRequest = new MockRequest("POST /form HTTP/1.0\r\n\r\n");
        sleepRequest = new MockRequest("GET /ping?sleep=4 HTTP/1.0\r\n\r\n");
        txtFileRequest = new MockRequest("GET /browse/" + testFilePath +  " HTTP/1.0\r\n\r\n");
        directoryRequest = new MockRequest("GET /browse");
        encodedPostRequest = new MockPostRequest("POST /form HTTP/1.0\r\n\r\n", "foo Hello!<>", "bar baz<>!");
        mockFactory = new MockFileResponseFactory(new MockResponse("mock content type", "bar"));
        factory = mockFactory;
        mockReader = new MockFileReader("foo");
        retriever = mockReader;
        builder = new HttpResponseBuilder(mockReader, factory);
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
    public void testPostFormResponse(){
        String response = builder.generateResponse(postRequest);
        String expectedBody = "foo = foo <br /> bar = bar";
        assertContentTypeAndBodyMatch(htmlContentType, expectedBody, response);
    }

    @Test
    public void testPostFormResponsesAreDecoded(){
        String response = builder.generateResponse(encodedPostRequest);
        String expectedBody = "foo = foo Hello!<> <br /> bar = bar baz<>!";
        assertContentTypeAndBodyMatch(htmlContentType, expectedBody, response);
    }

    @Test
    public void testSleepResponse(){
        String expectedBody = "<html><body>pong</body></html>";
        String response = builder.generateResponse(sleepRequest);
        assertContentTypeAndBodyMatch(htmlContentType, expectedBody, response);
        assertTrue(sleepRequest.sleepCalled());
    }

    @Test
    public void testDanceWithFileResponseFactory(){
        assertContentTypeAndBodyMatch("mock content type", "bar", builder.generateResponse(txtFileRequest));
        assertFileResponseFactoryCalledCorrectly(txtFileRequest, retriever);
    }

    @Test
    public void testResponseWithServiceRequest(){
        String contentType = "foo";
        String body = "bar";
        ServiceRequest serviceRequest = new MockServiceRequest();
        Response serviceResponse = new MockResponse(contentType, body);
        ResponseFactory responseFactory = new MockResponseFactory(serviceResponse);
        HttpResponseBuilder builder = new HttpResponseBuilder(retriever, mockFactory, responseFactory);
        assertContentTypeAndBodyMatch(contentType, body, builder.generateResponse(serviceRequest));
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
