package ResponseTests;

import com.arlandis.Responses.SleepResponse;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.Sleeper;
import mocks.MockRequest;
import mocks.MockSleeper;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SleepResponseTest {

    private MockSleeper mockSleeper = new MockSleeper();
    private Sleeper sleeper = mockSleeper;
    private Request sleepRequest = new MockRequest("GET /ping?sleep=5 HTTP/1.0 \r\n\r\n");

    @Test
    public void testSleepCalledOnSleeper(){
        SleepResponse response = new SleepResponse(sleepRequest, sleeper);
        response.body();
        assertTrue(mockSleeper.sleepCalledWith(5));
    }
}
