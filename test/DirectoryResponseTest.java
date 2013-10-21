import com.arlandis.Config;
import com.arlandis.Responses.FileResponses.DirectoryResponse;
import com.arlandis.interfaces.ResourceRetriever;
import mocks.MockFileReader;
import mocks.MockRequest;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectoryResponseTest {

    private MockFileReader mockFileReader = new MockFileReader("", "bar");
    private ResourceRetriever retriever = mockFileReader;
    private MockRequest request = new MockRequest("", "me");

    @Test
    public void testDirectoryResponsePollsReader(){
        DirectoryResponse response = new DirectoryResponse(request, retriever);
        assertEquals("bar<br />",response.body());
    }

    @Test
    public void testDirectoryResponsePollsConfig(){
        Config.instance().setRootDir("buzz");
        DirectoryResponse response = new DirectoryResponse(request, retriever);
        response.body();
        assertEquals("buzz/me", mockFileReader.history());
    }
}
