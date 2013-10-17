package com.arlandis.Responses;

import com.arlandis.interfaces.Response;

public class PongResponse implements Response {

    public String body() {
        return pongBody();
    }

    public String contentType(){
        return "Content-type: text/html";
    }

    private String addHeaderAndBodyTags(String content) {
        return "<html><body>" + content + "</body></html>";
    }

    private String pongBody() {
        return addHeaderAndBodyTags("pong");
    }

}
