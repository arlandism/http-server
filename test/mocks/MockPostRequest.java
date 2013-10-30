package mocks;

public class MockPostRequest extends MockRequest {

    private String body;

    public MockPostRequest(String headersToReturn, String body) {

        super(headersToReturn);
        this.body = body;
    }

    public String getBody(){
        return body;
    }
}
