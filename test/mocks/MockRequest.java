package mocks;


import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.Sleeper;

public class MockRequest implements Request {

    private String headers;
    private Boolean sleep = false;

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

}
