package mocks;

import com.arlandis.interfaces.ResourceRetriever;

public class MockFileReader implements ResourceRetriever {

    private String toReturn;
    private String history;

    public MockFileReader(String toReturn) {
        this.toReturn = toReturn;
    }

    public String getHistory() {
        return history;
    }

    public String retrieve(String requestedPath){
        history = requestedPath;
        return toReturn;
    }

}
