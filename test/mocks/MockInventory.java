package mocks;

import com.arlandis.interfaces.Inventory;

import java.util.HashSet;

public class MockInventory implements Inventory {

    private final Boolean valToReturn;

    public MockInventory(Boolean valToReturn){
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
