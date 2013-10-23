import com.arlandis.interfaces.TTTService;
import mocks.MockService;
import com.arlandis.Responses.TicTacToeService.Move;
import com.arlandis.Responses.ServiceResponse;
import com.arlandis.interfaces.Request;
import mocks.MockRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ServiceResponseTest {

    private ServiceResponse serviceResponse;
    private MockService mockService;
    private TTTService service;
    private Request request;

    @Before
    public void setUp(){
        mockService = new MockService("service response");
        service = mockService;
        String queryString = "/game?1=x&3=o";
        request = new MockRequest("GET " + queryString, queryString);
        serviceResponse = new ServiceResponse(service, request);
    }

    @Test
    public void testServiceResponseContentType(){
        assertEquals("Content-type: application/json", serviceResponse.contentType());
    }

    @Test
    public void testServiceResponseBody(){
        assertEquals("service response", serviceResponse.body());
    }

    @Test
    public void testServiceResponseQueriesServiceCorrectly(){
        Move[] moves = { new Move(1, "x"), new Move(3, "o") };
        serviceResponse.body();
        assertServiceCalledWith(moves);
    }

    private void assertServiceCalledWith(Move[] moves) {
        for (Move m: moves){
            assertTrue(mockService.calledWith(m));
        }
    }
}
