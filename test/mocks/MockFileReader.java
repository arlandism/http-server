package mocks;

import com.arlandis.interfaces.ResourceRetriever;

public class MockFileReader implements ResourceRetriever {

    private String retrieveReturnValue;
    private String retrieveDirReturnValue;
    private String history;

    public MockFileReader(String toReturn) {
        this.retrieveReturnValue = toReturn;
    }

    public MockFileReader(String forRetrieveToReturn, String retrieveDirReturnValue) {
        this.retrieveReturnValue = forRetrieveToReturn;
        this.retrieveDirReturnValue = retrieveDirReturnValue;
    }

    public String retrieve(String requestedPath){
        history = requestedPath;
        return retrieveReturnValue;
    }

    public String retrieveDirContents(String dirPath){
        return retrieveDirReturnValue;
    }

    public String history() {
        return history;
    }
}
