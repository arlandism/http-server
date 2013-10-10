package com.arlandis;

import com.arlandis.interfaces.RequestInterface;
import com.arlandis.interfaces.ResponseBuilderInterface;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ResponseBuilder implements ResponseBuilderInterface {

    private RequestInterface request;

    public ResponseBuilder(RequestInterface request) {
        this.request = request;
    }

    public ResponseBuilder() {
        //To change body of created methods use File | Settings | File Templates.
    }

    public String response() {
        String response;
        String head = "HTTP/1.0 200 OK";
        String contentType = "Content-type: text/html";
        String body;

        if (request.headers().startsWith("GET /form")) {
            body = formBody();
        } else if (request.headers().startsWith("POST ")) {
            body = formParams();
        } else if (request.headers().startsWith("GET /ping?sleep")){
            SleepImp sleeper = new SleepImp();
            request.sleep(sleeper);
            body = pongBody();
        } else{
            body = pongBody();
        }

        response = head + "\n" + contentType + "\n\r\n" + body;
        return response;
    }

    private String formBody(){
        return "<html><body>" +
               "<form method='post', action='/form'>" +
               "<label>foo<input name='foo'></label>" +
               "<br /><label>bar<input name='bar'></label>" +
               "<br /><input value='submit' type='submit'></form>";
    }

    private String pongBody(){
        return "<html><body>pong</body></html>";
    }

    private String formParams() {
        String LINE_BREAK = "<br />";
        return "foo = " + decodeValue(request.fooValue()) + LINE_BREAK + "bar = " + decodeValue(request.barValue());
    }

    private String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String generateResponse(RequestInterface request) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}