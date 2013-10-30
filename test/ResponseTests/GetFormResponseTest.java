package ResponseTests;

import com.arlandis.Responses.GetFormResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetFormResponseTest {

    @Test
    public void testGetFormResponse(){
        GetFormResponse response = new GetFormResponse();
        String expectedBody = "<html><body>" +
                "<form method='post', action='/form'>" +
                "<label>foo<input name='foo'></label>" +
                "<br /><label>bar<input name='bar'></label>" +
                "<br /><input value='submit' type='submit'>" +
                "</form></body></html>";
        assertEquals(expectedBody, response.body());
    }

}
