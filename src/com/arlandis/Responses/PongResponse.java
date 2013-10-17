package com.arlandis.Responses;

public class PongResponse {

    public String content() {
        return pongBody();
    }

    private String addHeaderAndBodyTags(String content) {
        return "<html><body>" + content + "</body></html>";
    }

    private String pongBody() {
        return addHeaderAndBodyTags("pong");
    }
}
