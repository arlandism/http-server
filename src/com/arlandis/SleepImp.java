package com.arlandis;

import com.Sleeper;

public class SleepImp implements Sleeper{

    public void sleep(Integer sleepTime){

        try {
            Thread.sleep(sleepTime);

        } catch (InterruptedException e) {
            System.out.println("Something woke the thread");
            e.printStackTrace();
        }
    }
}
