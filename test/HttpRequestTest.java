import com.arlandis.HttpRequest;
import com.arlandis.interfaces.Sleeper;
import mocks.MockSleeper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpRequestTest {

    private MockSleeper sleeper = new MockSleeper();
    private HttpRequest headlessRequest = new HttpRequest("");

    @Test
    public void testReadBytes(){
        String rawHeader = "Content-Length: 15";
        HttpRequest request = new HttpRequest(rawHeader);
        assertEquals(Integer.valueOf(15), request.bytesToRead());
    }

    @Test
    public void testHasBody(){
        String header = "Content-Length";
        HttpRequest request = new HttpRequest(header);
        assertTrue(request.hasBody()) ;
    }

    @Test
    public void testFooValue(){
        String body = "foo=hello&bar=hi";
        headlessRequest.setBody(body);
        assertEquals("hello", headlessRequest.fooValue());
    }

    @Test
    public void testBarValue(){
        String body = "foo=hello&bar=hi";
        headlessRequest.setBody(body);
        assertEquals("hi", headlessRequest.barValue());
    }

    @Test
    public void testSleepCanBeCalledWithQuery(){
        String rawHeader = "GET /ping?sleep=4\r\n";
        HttpRequest request = new HttpRequest(rawHeader);
        Sleeper s = sleeper;
        request.sleep(s);
        assertTrue(sleeper.sleepCalledWith(4));
    }
}
