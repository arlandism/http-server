package com.arlandis.Responses;

import com.arlandis.interfaces.Request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PostFormResponse {

    private Request request;

    public PostFormResponse(Request request){
        this.request = request;
    }

    public String content(){
        return formParams();
    }

    private String formParams() {
        String LINE_BREAK = "<br />";
        return "foo = " + decodeValue(request.fooValue()) + " " + LINE_BREAK + " bar = " + decodeValue(request.barValue());
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
