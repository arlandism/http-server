import com.arlandis.ServerThread;
import com.arlandis.interfaces.Responder;
import mocks.MockServer;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ServerThreadTest {

    @Test
    public void testServerRespondCalled(){
        MockServer mockServer = new MockServer();
        Responder server = mockServer;
        Runnable sThread = new ServerThread(server);
        sThread.run();
        assertTrue(mockServer.respondCalled());
    }
}
