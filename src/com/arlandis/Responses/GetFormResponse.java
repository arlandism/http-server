package com.arlandis.Responses;

public class GetFormResponse {

    public String content() {
        return formBody();
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
