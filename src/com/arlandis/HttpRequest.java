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
        final Integer contentHeaderLength = 16;
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
        Integer startOfRequestSection = requestHeaders.indexOf("/browse") + 8;
        Integer protocolIndex = requestHeaders.indexOf("HTTP");
        return requestHeaders.substring(startOfRequestSection, protocolIndex - 1).trim();
    }

    public void sleep(Sleeper sleeper) {
        sleeper.sleep(sleepTime());
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
