package com.arlandis.Responses;

import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.Response;
import com.arlandis.interfaces.Sleeper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SleepResponse implements Response {

    private Request request;
    private Sleeper sleeper;

    public SleepResponse(Request request, Sleeper sleeper){
        this.request = request;
        this.sleeper = sleeper;
    }
    public String body() {
        sleep();
        return (new PongResponse()).body();
    }

    public String contentType(){
        return (new PongResponse()).contentType();
    }

    private void sleep(){
       sleeper.sleep(sleepTime());
    }

    private Integer sleepTime() {
        Integer start = request.headers().indexOf("sleep") + 6;
        return parseHeadersForInt(start);
    }

    private Integer parseHeadersForInt(Integer indexToStartAt) {
        Pattern intsOnly = Pattern.compile("\\d+");
        Matcher matcher = intsOnly.matcher(request.headers().substring(indexToStartAt));
        matcher.find();
        String inputInt = matcher.group();
        return Integer.parseInt(inputInt);
    }
}
