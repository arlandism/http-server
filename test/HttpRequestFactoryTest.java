import com.arlandis.HttpRequestFactory;
import com.arlandis.interfaces.NetworkIO;
import mocks.MockNetworkIO;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class HttpRequestFactoryTest {

    private MockNetworkIO mockNetworkIO = new MockNetworkIO();
    private NetworkIO networkIO = mockNetworkIO;
    private HttpRequestFactory factory = new HttpRequestFactory();

    @Test
    public void testFactorySetsRequestHeaders() throws IOException {
        mockNetworkIO.addToOutputQueue("Fake Request Header");
        mockNetworkIO.addToOutputQueue("");
        NetworkIO networkIO = mockNetworkIO;
        assertEquals("Fake Request Header", factory.nextRequest(networkIO).headers());
    }

    @Test
    public void testFactorySetsRequestBody() throws IOException {
        mockNetworkIO.addToOutputQueue("Content-Length: 7");
        mockNetworkIO.addToOutputQueue("");
        mockNetworkIO.addToOutputQueue("foo=nom&bar=baz");
        assertEquals("nom", factory.nextRequest(networkIO).fooValue());
    }
}