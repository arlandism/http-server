package mocks;


import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.Sleeper;

public class MockRequest implements Request {

    private String headers;
    private Boolean sleep = false;
    private String resourceToReturn;

    public MockRequest(String headersToReturn){
       headers = headersToReturn;
    }

    public MockRequest(String headersToReturn, String resourceToReturn){
        headers = headersToReturn;
        this.resourceToReturn = resourceToReturn;
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

    public Boolean sleepCalled(){
        return sleep;
    }

    public void sleep(Sleeper sleeper){
        sleep = true;
    }

    @Override
    public String fooValue() {
        return "foo";
    }

    @Override
    public String barValue() {
        return "bar";
    }

    @Override
    public String requestedResource() {
        return resourceToReturn;
    }

}
