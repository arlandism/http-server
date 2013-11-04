package mocks;

import com.arlandis.interfaces.FeatureParser;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResponseBuilder;

import java.util.HashSet;

public class MockResponseBuilder implements ResponseBuilder {

    private String toReturn;
    private HashSet callArgs = new HashSet();

    public MockResponseBuilder(String toReturn) {
        this.toReturn = toReturn;
    }

    public String generateResponse(Request request, FeatureParser parser){
        callArgs.add(request);
        callArgs.add(parser);
        return toReturn;
    }

    public boolean calledWith(Object obj) {
        return callArgs.contains(obj);
    }
}
