package mocks;

import com.arlandis.interfaces.Toggler;

import java.util.HashSet;

public class MockToggler implements Toggler {

    private final Boolean valToReturn;

    public MockToggler(Boolean valToReturn){
        this.valToReturn = valToReturn;
    }

    private HashSet callArgs = new HashSet();

    public boolean calledWith(String requestHeader){
      return callArgs.contains(requestHeader);
    }

    @Override
    public Boolean isEnabled(String headers) {
      callArgs.add(headers);
      return valToReturn;
    }
}
