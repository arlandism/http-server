package mocks;

import com.arlandis.interfaces.FileResponseFactory;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.Response;

public class MockFileResponseFactory implements FileResponseFactory {

    private MockResponse toReturn;

    public MockFileResponseFactory(MockResponse response) {
        toReturn = response;
    }

    @Override
    public Response fileResponse(Request request, ResourceRetriever retriever) {
        return toReturn;
    }
}
