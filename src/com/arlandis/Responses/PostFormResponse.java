package com.arlandis.Responses;

import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PostFormResponse implements Response {

    private Request request;

    public PostFormResponse(Request request){
        this.request = request;
    }

    public String body(){
        return formParams();
    }

    public String contentType(){
        return "text/html";
    }
    private String formParams() {
        String LINE_BREAK = "<br />";
        return "foo = " + decodeValue(fooValue()) + " " + LINE_BREAK + " bar = " + decodeValue(barValue());
    }

    private String barValue() {
        Integer fooEnd = request.getBody().indexOf("&");
        Integer barBegin = request.getBody().indexOf("=", fooEnd) + 1;
        return request.getBody().substring(barBegin, request.getBody().length());
    }

    private String fooValue() {
        Integer fooBegin = request.getBody().indexOf("=") + 1;
        Integer fooEnd = request.getBody().indexOf("&");
        return request.getBody().substring(fooBegin, fooEnd);
    }

    private String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
