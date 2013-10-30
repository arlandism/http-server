import com.arlandis.FileResponseFactoryImp;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.Response;
import mocks.MockFileReader;
import mocks.MockRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FileResponseFactoryImpTest {

    private MockRequest textFileRequest = new MockRequest("GET /browse/foo.txt HTTP/1.0", "/browse/foo.txt");
    private MockRequest pngFileRequest = new MockRequest("GET /browse/foo.png HTTP/1.0", "/browse/foo.png");
    private MockRequest jpegFileRequest = new MockRequest("GET /browse/foo.jpeg HTTP/1.0", "/browse/foo.jpeg");
    private MockRequest jpgFileRequest = new MockRequest("GET /browse/foo.jpg HTTP/1.0", "/browse/foo.jpg");
    private MockRequest pdfFileRequest = new MockRequest("GET /browse/foo.pdf HTTP/1.0", "/browse/foo.pdf");
    private MockRequest bmpFileRequest = new MockRequest("GET /browse/foo.bmp HTTP/1.0", "/browse/foo.bmp");
    private MockRequest directoryRequestWithSlash = new MockRequest("GET /browse/foo/", "/browse/foo/");
    private MockFileReader reader = new MockFileReader("fake data");
    private FileResponseFactoryImp factory = new FileResponseFactoryImp();
    private Map<Request, String> requestToExtension = new HashMap<Request, String>();

    @Before
    public void setUp(){
        requestToExtension.put(textFileRequest, "text/html");
        requestToExtension.put(pngFileRequest, "image/png");
        requestToExtension.put(jpegFileRequest, "image/jpeg");
        requestToExtension.put(jpgFileRequest, "image/jpeg");
        requestToExtension.put(pdfFileRequest, "application/pdf");
        requestToExtension.put(bmpFileRequest, "image/bmp");
        requestToExtension.put(directoryRequestWithSlash, "text/html");
        requestToExtension.put(new MockRequest("", "bar.clj"), "text/html");
    }

    @Test
    public void testContentTypesAgainstMockRequests(){
        for (Request request: requestToExtension.keySet()){
            String expectedType = requestToExtension.get(request);
            Response response = factory.fileResponse(request, reader);
            assertCorrectContentType(expectedType, response);
        }
    }

    public void assertCorrectContentType(String extension, Response response){
        assertEquals(extension, response.contentType());
    }


}
