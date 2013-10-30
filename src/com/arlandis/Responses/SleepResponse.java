package com.arlandis.Responses;

import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.Response;
import com.arlandis.interfaces.Sleeper;

public class SleepResponse implements Response {

    private Request request;
    private Sleeper sleeper;

    public SleepResponse(Request request, Sleeper sleeper){
        this.request = request;
        this.sleeper = sleeper;
    }
    public String body() {
        request.sleep(sleeper);
        return (new PongResponse()).body();
    }

    public String contentType(){
        return "text/html";
    }
}
