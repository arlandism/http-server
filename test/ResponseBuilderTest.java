import com.arlandis.ResponseBuilder;
import mocks.MockRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ResponseBuilderTest {

    private MockRequest request;
    private MockRequest formRequest;

    @Before
    public void setUp(){
        request = new MockRequest("GET /ping HTTP/1.0\r\n\r\n");
        formRequest = new MockRequest("GET /form HTTP/1.0\r\n\r\n");
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
}
