import com.arlandis.interfaces.Sleeper;
import com.arlandis.Request;
import mocks.MockSleeper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestTest {

    private MockSleeper sleeper = new MockSleeper();

    @Test
    public void testReadBytes(){
        String rawHeader = "Content-Length: 15";
        Request request = new Request(rawHeader);
        assertEquals(Integer.valueOf(15), request.bytesToRead());
    }

    @Test
    public void testSleepCanBeCalledWithQuery(){
        String rawHeader = "GET /ping?sleep=4\r\n";
        Request request = new Request(rawHeader);
        Sleeper s = sleeper;
        request.sleep(s);
        assertTrue(sleeper.sleepCalledWith(4));
    }
}
