import com.arlandis.HttpRequestFactory;
import com.arlandis.interfaces.NetworkIO;
import mocks.MockNetworkIO;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class HttpRequestFactoryTest {

    private MockNetworkIO mockNetworkIO = new MockNetworkIO();
    private NetworkIO networkIO = mockNetworkIO;
    private HttpRequestFactory factory = new HttpRequestFactory(networkIO);

    @Test
    public void testFactorySetsRequestHeaders() throws IOException {
        mockNetworkIO.addToOutputQueue("Fake Request Header");
        mockNetworkIO.addToOutputQueue("");
        assertEquals("Fake Request Header", factory.nextRequest().headers());
    }

    @Test
    public void testFactorySetsRequestBody() throws IOException {
        mockNetworkIO.addToOutputQueue("Content-Length: 7");
        mockNetworkIO.addToOutputQueue("");
        mockNetworkIO.addToOutputQueue("foo=nom&bar=baz");
        assertEquals("foo=nom&bar=baz", factory.nextRequest().getBody());
    }
}
