package com.arlandis.Responses;

import com.arlandis.interfaces.Response;

public class GetFormResponse implements Response {

    public String body() {
        return formBody();
    }

    public String contentType(){
        return "text/html";
    }

    private String formBody() {

        return addHeaderAndBodyTags("<form method='post', action='/form'>" +
                "<label>foo<input name='foo'></label>" +
                "<br /><label>bar<input name='bar'></label>" +
                "<br /><input value='submit' type='submit'></form>");
    }

    private String addHeaderAndBodyTags(String content) {
        return "<html><body>" + content + "</body></html>";
    }

}
