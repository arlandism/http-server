package mocks;

import com.arlandis.HttpRequest;
import com.arlandis.interfaces.RequestFactory;
import com.arlandis.interfaces.NetworkIO;

import java.io.IOException;

public class MockFactory implements RequestFactory {
    @Override
    public HttpRequest nextRequest(NetworkIO networkIO) throws IOException {
        return null;
    }
}
