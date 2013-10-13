package mocks;

import com.arlandis.interfaces.Responder;

public class MockServer implements Responder {
    private Boolean respondCalled = false;

    @Override
    public void respond() {
      respondCalled = true;
    }

    public Boolean respondCalled(){
        return respondCalled;
    }
}
