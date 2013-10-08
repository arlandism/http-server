package mocks;


import com.arlandis.RequestInterface;

public class MockRequest implements RequestInterface{

    private String headers;

    public MockRequest(String headersToReturn){
        headers = headersToReturn;
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

    public void setBody(String body){
    }

    public String getBody() {
        return null;
    }

}
