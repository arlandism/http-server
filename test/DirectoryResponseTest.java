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

    @Test
    public void testDirectoryResponseMakesLinksWithoutBrowse(){
        DirectoryResponse response = new DirectoryResponse("", retriever);
        assertEquals("<a href='/browse/bar'>bar</a><br />",response.body());
    }

    @Test
    public void testDirectoryResponseWithBrowserIncluded(){
        DirectoryResponse response = new DirectoryResponse("/browse/foo/", retriever);
        assertEquals("<a href='/browse/foo/bar'>bar</a><br />", response.body());
    }

    @Test
    public void testDirectoryResponsePollsConfig(){
        Config.instance().setRootDir("buzz");
        DirectoryResponse response = new DirectoryResponse("browse/", retriever);
        response.body();
        assertEquals("buzz/", mockFileReader.history());
    }
}
