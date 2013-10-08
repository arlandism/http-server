package com.arlandis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request implements RequestInterface{
    private String requestHeaders;
    private final Integer NUM_DELIMITERS = 6;
    private String body;

    public Request(String request) {
        requestHeaders = request;
    }

    public Integer bytesToRead() {
        Integer START_LOCATION = requestHeaders.indexOf("Content-Length: ") + 16;
        return parseHeadersForInt(START_LOCATION);
    }

    public Boolean hasBody(){
        return requestHeaders.contains("Content-Length");
    }

    public String headers(){
        return requestHeaders;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody(){
        return body;
    }

    public Integer sleepTime() {
        Integer start = requestHeaders.indexOf("sleep") + 6;
        return parseHeadersForInt(start);
    }

    private Integer parseHeadersForInt(Integer indexToStartAt){
        Pattern intsOnly = Pattern.compile("\\d+");
        Matcher matcher = intsOnly.matcher(requestHeaders.substring(indexToStartAt));
        matcher.find();
        String inputInt = matcher.group();
        return Integer.parseInt(inputInt);
    }
}
