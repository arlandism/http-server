package com.arlandis;

public class RequestParser {
    private String requestToParse;

    public RequestParser(String request) {
        requestToParse = request;
    }

    public Integer bytesToRead() {
        Integer START_LOCATION = requestToParse.indexOf("Content-Length: ") + 16;
        Integer END_LOCATION = START_LOCATION + 1;
        return Integer.parseInt(requestToParse.substring(START_LOCATION, END_LOCATION));
    }
}
