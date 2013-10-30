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

    private MockRequest txtFileRequest;
    private MockRequest serviceRequest;
    private HttpResponseBuilder builder;
    private String testFilePath = "foo/bar/baz.txt";
    private FileResponseFactory factory;
    private MockFileResponseFactory mockFactory;
    private MockFileReader mockReader;
    private ResourceRetriever retriever;
    private TTTService service;

    @Before
    public void setUp(){
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
