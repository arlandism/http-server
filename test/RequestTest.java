import com.arlandis.Request;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestTest {

    @Test
    public void testReadBytes(){
        String rawHeader = "Content-Length: 15";
        Request request = new Request(rawHeader);
        assertEquals(Integer.valueOf(15), request.bytesToRead());
    }

    @Test
    public void testSleepTime(){
        String rawHeader = "GET /ping?sleep=16\r\n";
        Request request = new Request(rawHeader);
        assertEquals(Integer.valueOf(16), request.sleepTime());
    }
}
