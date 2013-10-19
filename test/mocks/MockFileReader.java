package mocks;

import com.arlandis.interfaces.ResourceRetriever;

public class MockFileReader implements ResourceRetriever {

    private String retrieveReturnValue;
    private String[] retrieveDirReturnValue = new String[1];
    private String history;

    public MockFileReader(String toReturn) {
        this.retrieveReturnValue = toReturn;
    }

    public MockFileReader(String forRetrieveToReturn, String retrieveDirReturnValue) {
        this.retrieveReturnValue = forRetrieveToReturn;
        this.retrieveDirReturnValue[0] = retrieveDirReturnValue;
    }

    public String retrieve(String requestedPath){
        history = requestedPath;
        return retrieveReturnValue;
    }

    public String[] retrieveDirContents(String dirPath){
        history = dirPath;
        return retrieveDirReturnValue;
    }

    public String history() {
        return history;
    }
}
