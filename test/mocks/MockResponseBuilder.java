package mocks;

import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResponseBuilder;
import com.arlandis.interfaces.Toggler;

import java.util.HashSet;

public class MockResponseBuilder implements ResponseBuilder {

    private String toReturn;
    private HashSet callArgs = new HashSet();

    public MockResponseBuilder(String toReturn) {
        this.toReturn = toReturn;
    }

    public String generateResponse(Request request, Toggler toggler){
        callArgs.add(request);
        callArgs.add(toggler);
        return toReturn;
    }

    public boolean calledWith(Object obj) {
        return callArgs.contains(obj);
    }
}
