package mocks;

import com.arlandis.HttpRequest;
import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.RequestFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: arlandislawrence
 * Date: 10/11/13
 * Time: 4:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockRequestFactory implements RequestFactory{
    Request toReturn;
    public MockRequestFactory(Request toReturn){
        this.toReturn = toReturn;
    }

    @Override
    public Request nextRequest(NetworkIO networkIO) throws IOException {
        return toReturn;
    }
}
