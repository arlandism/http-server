package mocks;

import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.RequestFactory;

import java.io.IOException;

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
