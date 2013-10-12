package com.arlandis;

import com.arlandis.interfaces.Sleeper;

public class

        ThreadSleeper implements Sleeper {

    public void sleep(Integer sleepTime){

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
