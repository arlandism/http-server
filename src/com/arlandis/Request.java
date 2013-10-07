package com.arlandis;

public class Request {
    private String requestToParse;
    private final Integer NUM_DELIMITERS = 6;
    public char[] requestBodyStorage;

    public Request(String request) {
        requestToParse = request;
        requestBodyStorage = new char[bytesToRead()];
    }

    public Integer bytesToRead() {
        Integer START_LOCATION = requestToParse.indexOf("Content-Length: ") + 16;
        Integer END_LOCATION = START_LOCATION + 1;
        return Integer.parseInt(requestToParse.substring(START_LOCATION, END_LOCATION)) + NUM_DELIMITERS;
    }

    public char[] getRequestBodyStorage(){
        return requestBodyStorage;
    }

    public String getRequestBody() {
        return new String(getRequestBodyStorage());
    }

    public Boolean hasBody(){
        return requestToParse.contains("Content-Length");
    }
}
