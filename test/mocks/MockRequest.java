package mocks;


import com.arlandis.RequestInterface;

public class MockRequest implements RequestInterface{

    private String headers;
    private String body;

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
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

}
