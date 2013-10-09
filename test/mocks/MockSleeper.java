package mocks;

import com.Sleeper;

public class MockSleeper implements Sleeper {

    private Boolean sleepCalled = false;
    private Integer sleepParam;

    public Boolean sleepCalledWith(Integer sleepLength){
        return sleepCalled && sleepParam == sleepLength;
    }

    public void sleep(Integer sleepLength){
        sleepCalled = true;
        sleepParam = sleepLength;
    }
}
