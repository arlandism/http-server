import com.arlandis.FileResponseFactory;
import com.arlandis.interfaces.Response;
import mocks.MockFileReader;
import mocks.MockRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileResponseFactoryTest {

    private MockRequest textFileRequest = new MockRequest("GET /browse/foo.txt HTTP/1.0");
    private MockRequest pngFileRequest = new MockRequest("GET /browse/foo.png HTTP/1.0");
    private MockRequest jpegFileRequest = new MockRequest("GET /browse/foo.jpeg HTTP/1.0");
    private MockRequest jpgFileRequest = new MockRequest("GET /browse/foo.jpg HTTP/1.0");
    private MockRequest pdfFileRequest = new MockRequest("GET /browse/foo.pdf HTTP/1.0");
    private MockFileReader reader = new MockFileReader("fake data");

    @Test
    public void testContentTypeWithRequestForTextFile(){
        Response response = FileResponseFactory.fileResponse(textFileRequest, reader);
        assertEquals("Content-type: text/html", response.contentType());
    }

    @Test
    public void testContentTypeWithRequestForPngFile(){
        Response response = FileResponseFactory.fileResponse(pngFileRequest, reader);
        assertEquals("Content-type: image/png", response.contentType());
    }

    @Test
    public void testContentTypeWithRequestForJpegFile(){
        Response response = FileResponseFactory.fileResponse(jpegFileRequest, reader);
        assertEquals("Content-type: image/jpeg", response.contentType());
        Response response1 = FileResponseFactory.fileResponse(jpgFileRequest, reader);
        assertEquals("Content-type: image/jpeg", response1.contentType());
    }

    @Test
    public void testContentTypeWithRequestForPdfFile(){
        Response response = FileResponseFactory.fileResponse(pdfFileRequest, reader);
        assertEquals("Content-type: application/pdf", response.contentType());

    }
}
