package mocks;

import com.arlandis.interfaces.Sleeper;

public class MockSleeper implements Sleeper {

    private Boolean sleepCalled = false;
    private Integer sleepParam;

    public Boolean sleepCalledWith(Integer sleepLength){
        return sleepCalled && sleepParam.equals(sleepLength);
    }

    public void sleep(Integer sleepLength){
        sleepCalled = true;
        sleepParam = sleepLength;
    }
}
