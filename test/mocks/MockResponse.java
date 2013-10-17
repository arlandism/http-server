package mocks;

import com.arlandis.interfaces.Response;

public class MockResponse implements Response {

    private String contentTypeToReturn;
    private String bodyToReturn;

    public MockResponse(String contentTypeToReturn, String bodyToReturn) {
        this.contentTypeToReturn = contentTypeToReturn;
        this.bodyToReturn = bodyToReturn;
    }


    @Override
    public String body() {
        return bodyToReturn;
    }

    @Override
    public String contentType() {
        return "Content-type: " + contentTypeToReturn;
    }
}
