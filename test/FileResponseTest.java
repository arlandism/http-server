import com.arlandis.Config;
import com.arlandis.Responses.FileResponses.FileResponse;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import mocks.MockFileReader;
import mocks.MockRequest;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileResponseTest {
    MockFileReader mockReader = new MockFileReader("data from mock reader");
    ResourceRetriever retriever = mockReader;
    MockRequest request = new MockRequest("", "bar");

    class TestFileResponse extends FileResponse{

        public TestFileResponse(Request request, ResourceRetriever retriever){
            super(request, retriever);
        }

    }

    private FileResponse fileResponse = new TestFileResponse(request, retriever);

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
