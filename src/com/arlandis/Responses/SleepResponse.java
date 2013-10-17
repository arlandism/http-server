package com.arlandis.Responses;

import com.arlandis.ThreadSleeper;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.Sleeper;

public class SleepResponse {

    private Request request;
    private Sleeper sleeper;

    public SleepResponse(Request request, Sleeper sleeper){
        this.request = request;
        this.sleeper = sleeper;
    }
    public String content() {
        ThreadSleeper sleeper = new ThreadSleeper();
        request.sleep(sleeper);
        return (new PongResponse()).content();
    }
}
