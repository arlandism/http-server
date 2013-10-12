import com.arlandis.HttpResponseBuilder;
import mocks.MockPostRequest;
import mocks.MockRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpResponseBuilderTest {

    private MockRequest pingRequest;
    private MockRequest formRequest;
    private MockRequest postRequest;
    private MockRequest sleepRequest;
    private MockPostRequest encodedPostRequest;
    private HttpResponseBuilder builder = new HttpResponseBuilder();

    @Before
    public void setUp(){
        pingRequest = new MockRequest("GET /ping HTTP/1.0\r\n\r\n");
        formRequest = new MockRequest("GET /form HTTP/1.0\r\n\r\n");
        postRequest = new MockRequest("POST /form HTTP/1.0\r\n\r\n");
        sleepRequest = new MockRequest("GET /ping?sleep=4 HTTP/1.0\r\n\r\n");
        encodedPostRequest = new MockPostRequest("POST /form HTTP/1.0\r\n\r\n", "foo Hello!<>", "bar baz<>!");
    }

    @Test
    public void testGetPingResponse(){
        String expected = "HTTP/1.0 200 OK" + "\r\n" +
                          "Content-type: text/html" + "\r\n\r\n" +
                          "<html><body>pong</body></html>";
        assertEquals(expected, builder.generateResponse(pingRequest));
    }

    @Test
    public void testGetFormResponse(){
        String response = builder.generateResponse(formRequest);
        String expected = "HTTP/1.0 200 OK" + "\r\n" +
                          "Content-type: text/html" + "\r\n\r\n" +
                          "<html><body>" +
                          "<form method='post', action='/form'>" +
                          "<label>foo<input name='foo'></label>" +
                          "<br /><label>bar<input name='bar'></label>" +
                          "<br /><input value='submit' type='submit'>" +
                          "</form>";
        assertEquals(expected, response);
    }

    @Test
    public void testPostFormResponse(){
        String response = builder.generateResponse(postRequest);
        String expected = "HTTP/1.0 200 OK" + "\r\n" +
                          "Content-type: text/html" + "\r\n\r\n" +
                          "foo = foo <br /> bar = bar";
        assertEquals(expected, response);
    }

    @Test
    public void testPostFormResponsesAreDecoded(){
        String response = builder.generateResponse(encodedPostRequest);
        String expected =   "HTTP/1.0 200 OK" + "\r\n" +
                            "Content-type: text/html" + "\r\n\r\n" +
                            "foo = foo Hello!<> <br /> bar = bar baz<>!";
        assertEquals(expected, response);
    }

    @Test
    public void testSleepResponse(){
        String expected = "HTTP/1.0 200 OK" + "\r\n" +
                          "Content-type: text/html" + "\r\n\r\n" +
                          "<html><body>pong</body></html>";
        String response = builder.generateResponse(sleepRequest);
        assertTrue(sleepRequest.sleepCalled());
        assertEquals(expected, response);
    }
}
