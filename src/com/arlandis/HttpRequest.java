package com.arlandis;

import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.Sleeper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest implements Request {

    private String requestHeaders;
    private String body;

    public HttpRequest(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public Integer bytesToRead() {
        Integer contentHeaderLength = 16;
        Integer START_LOCATION = requestHeaders.indexOf("Content-Length: ") + contentHeaderLength;
        return parseHeadersForInt(START_LOCATION);
    }

    public Boolean hasBody() {
        return requestHeaders.contains("Content-Length");
    }

    public String headers() {
        return requestHeaders;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String barValue() {
        Integer fooEnd = body.indexOf("&");
        Integer barBegin = body.indexOf("=", fooEnd) + 1;
        return body.substring(barBegin, body.length());
    }

    public String fooValue() {
        Integer fooBegin = body.indexOf("=") + 1;
        Integer fooEnd = body.indexOf("&");
        return body.substring(fooBegin, fooEnd);
    }

    public String requestedResource(){

        String resource = requestHeaders.substring(startOfRequest(), endOfRequest());
        return resource;
        //f (resource.endsWith("/")){
        //    return "/";
        //} else {
        //    return resource;
        //}

    }

    public void sleep(Sleeper sleeper) {
        sleeper.sleep(sleepTime());
    }

    private boolean requestForDirectory() {
        Integer indexOfRequestPrefix = 7;
        String resourcePrefix = requestHeaders.substring(browseIndex() + indexOfRequestPrefix, startOfRequest());
        return resourcePrefix.equals(" ") || (resourcePrefix.equals("/") && isEndOfRequest());
    }

    private boolean isEndOfRequest() {
        return requestHeaders.substring(startOfRequest(), protocolIndex()).equals(" ");
    }

    private Integer startOfRequest() {
        return requestHeaders.indexOf("/");
    }

    private Integer endOfRequest(){
        return protocolIndex() - 1;
    }

    private Integer browseIndex(){
        return requestHeaders.indexOf("/browse");
    }

    private Integer protocolIndex(){
        return requestHeaders.indexOf("HTTP");
    }

    private Integer sleepTime() {
        Integer start = requestHeaders.indexOf("sleep") + 6;
        return parseHeadersForInt(start);
    }

    private Integer parseHeadersForInt(Integer indexToStartAt) {
        Pattern intsOnly = Pattern.compile("\\d+");
        Matcher matcher = intsOnly.matcher(requestHeaders.substring(indexToStartAt));
        matcher.find();
        String inputInt = matcher.group();
        return Integer.parseInt(inputInt);
    }

}
