import com.arlandis.HttpRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpRequestTest {

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
    public void testRequestedResource(){
        String rawHeader = "GET /browse/bar/foo.txt HTTP/1.0";
        HttpRequest request = new HttpRequest(rawHeader);
        assertEquals("/browse/bar/foo.txt", request.requestedResource());
    }

    @Test
    public void testSlashReturnsSlash(){
        String rawHeader = "GET /browse/ HTTP/1.0";
        HttpRequest request = new HttpRequest(rawHeader);
        assertEquals("/browse/", request.requestedResource());
    }
}
