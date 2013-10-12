package mocks;

import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResponseBuilder;

public class MockResponseBuilder implements ResponseBuilder {

    private String toReturn;
    private Request calledWith;

    public MockResponseBuilder(String toReturn) {
        this.toReturn = toReturn;
    }

    @Override
    public String generateResponse(Request request) {
        calledWith = request;
        return toReturn;
    }

    public Request history() {
        return calledWith;
    }
}
