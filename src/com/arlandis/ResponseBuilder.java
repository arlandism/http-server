package com.arlandis;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ResponseBuilder {

    private RequestInterface request;

    public ResponseBuilder(RequestInterface request){
       this.request = request;
    }

    public String response(){
        String response;
        String head = "HTTP/1.0 200 OK";
        String contentType = "Content-type: text/html";
        String body;
        String formBody = "<html><body>" +
                          "<form method='post', action='/form'>" +
                          "<label>foo<input name='foo'></label>" +
                          "<br /><label>bar<input name='bar'></label>" +
                          "<br /><input value='submit' type='submit'></form>";

        if (request.headers().startsWith("GET /form")){
            body = formBody;
           }
        else if (request.headers().startsWith("POST")){
            body = formParams();
        }
        else{
            body = "<html><body>pong</body></html>";
        }

        response = head + "\n" + contentType + "\n\r\n" + body;
        return response;
    }

    private String formParams() {
        String LINE_BREAK = "<br />";
        return "foo = " + decodeValue(request.fooValue()) + LINE_BREAK + "bar = " + decodeValue(request.barValue());
    }

  //  fooValue(requestBody)
    //barValue(requestBody)

    private String fooValue(String requestBody){
        Integer fooBegin = requestBody.indexOf("=") + 1;
        Integer fooEnd = requestBody.indexOf("&");
        return decodeValue(requestBody.substring(fooBegin, fooEnd));
    }

    private String barValue(String requestBody){
        Integer fooEnd = requestBody.indexOf("&");
        Integer barBegin = requestBody.indexOf("=", fooEnd) + 1;
        return decodeValue(requestBody.substring(barBegin, requestBody.length()));
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