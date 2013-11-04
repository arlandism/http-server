package ResponseTests;

import com.arlandis.Responses.PostFormResponse;
import com.arlandis.interfaces.Request;
import mocks.MockRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PostFormResponseTest {

    private Request request = new MockRequest("POST /form HTTP/1.0", "/form");

    @Test
    public void testPostFormResponseBody() {
        request.setBody("foo=foo Hello!<>&bar=bar baz<>!");
        PostFormResponse response = new PostFormResponse(request);
        String expectedBody = "foo = foo Hello!<> <br /> bar = bar baz<>!";
        assertEquals(expectedBody, response.body());
    }
}
