package mocks;

import com.arlandis.interfaces.FileResponseFactory;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.Response;

public class MockFileResponseFactory implements FileResponseFactory {

    private MockResponse toReturn;
    private Object[] history = new Object[2];

    public MockFileResponseFactory(MockResponse response) {
        toReturn = response;
    }

    @Override
    public Response fileResponse(Request request, ResourceRetriever retriever) {
        history[0] = request;
        history[1] = retriever;
        return toReturn;
    }

    public Object[] history() {
        return history;
    }
}
