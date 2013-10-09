package mocks;


import com.Sleeper;
import com.arlandis.RequestInterface;

public class MockRequest implements RequestInterface{

    private String headers;
    private String body;
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

    public void setBody(String body){
        this.body = body;
    }

    public String fooValue(){
        return "foo bar baz<>";
    }

    public String barValue(){
        return "bar foo baz<>";
    }

    public Boolean sleepCalled(){
        return sleep;
    }

    public void sleep(Sleeper sleeper){
        sleep = true;
    }

}
