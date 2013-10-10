import com.arlandis.ResponseBuilder;
import mocks.MockRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ResponseBuilderTest {

    private MockRequest request;
    private MockRequest formRequest;
    private MockRequest postRequest;
    private MockRequest sleepRequest;

    @Before
    public void setUp(){
        request = new MockRequest("GET /ping HTTP/1.0\r\n\r\n");
        formRequest = new MockRequest("GET /form HTTP/1.0\r\n\r\n");
        postRequest = new MockRequest("POST /form HTTP/1.o\r\n\r\n");
        sleepRequest = new MockRequest("GET /ping?sleep=4 HTTP/1.0\r\n\r\n");
    }

    @Test
    public void testResponseStatus(){
        ResponseBuilder builder = new ResponseBuilder(request);
        String response = builder.response();
        assertTrue(response.startsWith("HTTP/1.0 200 OK"));
    }

    @Test
    public void testResponseContentType() {
        ResponseBuilder builder = new ResponseBuilder(request);
        String response = builder.response();
        assertTrue(response.contains("Content-type: text/html"));
    }

    @Test
    public void testResponseBody() {
        ResponseBuilder builder = new ResponseBuilder(request);
        String response = builder.response();
        assertTrue(response.contains("<html><body>pong</body></html>"));
    }

    @Test
    public void testFormResponse(){
        ResponseBuilder builder = new ResponseBuilder(formRequest);
        String response = builder.response();
        String formBody = "<html><body>" +
                          "<form method='post', action='/form'>" +
                          "<label>foo<input name='foo'></label>" +
                          "<br /><label>bar<input name='bar'></label>" +
                          "<br /><input value='submit' type='submit'>";
        assertTrue(response.contains(formBody));
    }

    @Test
    public void testResponseDecoded(){
        ResponseBuilder builder = new ResponseBuilder(postRequest);
        String response = builder.response();
        assertTrue(response.contains("foo = foo bar baz<>"));
        assertTrue(response.contains("bar = bar foo baz<>"));
    }

    @Test
    public void testSleepResponse(){
        ResponseBuilder builder = new ResponseBuilder(sleepRequest);
        String response = builder.response();
        assertTrue(sleepRequest.sleepCalled());
        assertTrue(response.contains("<html><body>pong</body></html>"));
    }
}
