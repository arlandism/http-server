package ResponseTests;

import com.arlandis.Responses.PongResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PongResponseTest {

    @Test
    public void testGetPingResponse(){
        PongResponse response = new PongResponse();
        String expectedBody = "<html><body>pong</body></html>";
        assertEquals(expectedBody, response.body());
    }

}
