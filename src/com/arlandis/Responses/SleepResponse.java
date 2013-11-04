package com.arlandis.Responses;

import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.Response;
import com.arlandis.interfaces.Sleeper;

public class SleepResponse implements Response {

    private Request request;
    private Sleeper sleeper;

    public SleepResponse(Request request, Sleeper sleeper) {
        this.request = request;
        this.sleeper = sleeper;
    }

    public String body() {
        sleep();
        return (new PongResponse()).body();
    }

    public String contentType() {
        return (new PongResponse()).contentType();
    }

    private void sleep() {
        sleeper.sleep(sleepTime());
    }

    private Integer sleepTime() {
        Integer start = request.headers().indexOf("sleep") + 6;
        Integer end = request.headers().indexOf("HTTP") - 1;
        return Integer.valueOf(request.headers().substring(start, end));
    }
}
