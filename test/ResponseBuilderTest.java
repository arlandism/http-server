import com.arlandis.HttpResponseBuilder;
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
        HttpResponseBuilder builder = new HttpResponseBuilder(request);
        String response = builder.response();
        assertTrue(response.startsWith("HTTP/1.0 200 OK"));
    }

    @Test
    public void testResponseContentType() {
        HttpResponseBuilder builder = new HttpResponseBuilder(request);
        String response = builder.response();
        assertTrue(response.contains("Content-type: text/html"));
    }

    @Test
    public void testResponseBody() {
        HttpResponseBuilder builder = new HttpResponseBuilder(request);
        String response = builder.response();
        assertTrue(response.contains("<html><body>pong</body></html>"));
    }

    @Test
    public void testFormResponse(){
        HttpResponseBuilder builder = new HttpResponseBuilder(formRequest);
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
        HttpResponseBuilder builder = new HttpResponseBuilder(postRequest);
        String response = builder.response();
        assertTrue(response.contains("foo = foo bar baz<>"));
        assertTrue(response.contains("bar = bar foo baz<>"));
    }

    @Test
    public void testSleepResponse(){
        HttpResponseBuilder builder = new HttpResponseBuilder(sleepRequest);
        String response = builder.response();
        assertTrue(sleepRequest.sleepCalled());
        assertTrue(response.contains("<html><body>pong</body></html>"));
    }
}
