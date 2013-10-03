import com.arlandis.ResponseBuilder;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ResponseBuilderTest {

    @Test
    public void testResponseStatus(){
        ResponseBuilder builder = new ResponseBuilder("GET /ping HTTP/1.0\r\n\r\n");
        String response = builder.response();
        assertTrue(response.startsWith("HTTP/1.0 200 OK"));
    }

    @Test
    public void testResponseContentType() {
        ResponseBuilder builder = new ResponseBuilder("GET /ping HTTP/1.0\r\n\r\n");
        String response = builder.response();
        assertTrue(response.contains("Content-type: text/html"));
    }

    @Test
    public void testResponseBody() {
        ResponseBuilder builder = new ResponseBuilder("GET /ping HTTP/1.0\r\n\r\n");
        String response = builder.response();
        assertTrue(response.contains("<html><body>pong</body></html>"));
    }

    @Test
    public void testFormResponse(){
        ResponseBuilder builder = new ResponseBuilder("GET /form HTTP/1.0\r\n\r\n");
        String response = builder.response();
        String formBody = "<html><body>" +
                          "<form method='post', action='/form'>" +
                          "<label><input name='foo'>foo</label>" +
                          "<label><input name='bar'>bar</label>";
        assertTrue(response.contains(formBody));
    }
}
