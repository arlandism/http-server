import com.arlandis.Config;
import com.arlandis.Responses.FileResponses.FileResponse;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import mocks.MockFileReader;
import mocks.MockRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileResponseTest {
    private MockFileReader mockReader = new MockFileReader("data from mock reader");
    private ResourceRetriever retriever = mockReader;
    private MockRequest mockRequest = new MockRequest("", "bar");
    private Request request = mockRequest;
    private FileResponse fileResponse = new FileResponse(request, retriever);

    @Test
    public void testFileResponseBodyPollsResourceRetriever(){
       assertEquals("data from mock reader", fileResponse.body());
    }

    @Test
    public void testReaderPolledWithConfigDir(){
        Config.instance().setRootDir("baz");
        fileResponse.body();
        assertEquals("baz/bar", mockReader.history());
    }

}
