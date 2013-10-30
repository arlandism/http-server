package mocks;


import com.arlandis.interfaces.Request;

public class MockRequest implements Request {

    private String headers;
    private String resourceToReturn;

    public MockRequest(String headersToReturn){
       headers = headersToReturn;
    }

    public MockRequest(String headersToReturn, String resourceToReturn){
        headers = headersToReturn;
        this.resourceToReturn = resourceToReturn;
    }

    @Override
    public String getBody() {
        return "";
    }

    public String headers(){
        return headers;
    }

    public Integer bytesToRead(){
        return 0;
    }

    public Boolean hasBody(){
        return false;
    }

    @Override
    public void setBody(String body) {
    }

    @Override
    public String requestedResource() {
        return resourceToReturn;
    }

}
