import com.arlandis.Responses.PostFormResponse;
import com.arlandis.interfaces.Request;
import mocks.MockPostRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PostFormResponseTest {

    private Request request = new MockPostRequest("POST /form HTTP/1.0", "foo=foo Hello!<>&bar=bar baz<>!");

    @Test
    public void testPostFormResponseBody() {
        PostFormResponse response = new PostFormResponse(request);
        String expectedBody = "foo = foo Hello!<> <br /> bar = bar baz<>!";
        assertEquals(expectedBody, response.body());
    }
}
