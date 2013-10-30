package ResponseTests;

import com.arlandis.Responses.FeatureNotFoundResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FeatureNotFoundResponseTest {
    @Test
    public void testFeatureNotFoundResponse(){
        FeatureNotFoundResponse response = new FeatureNotFoundResponse();
        String expectedBody = "<html><body>The feature you're looking for can't be found.</body></html>";
        assertEquals(expectedBody, response.body());
    }
}
