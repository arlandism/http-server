package com.arlandis;

public class ResponseBuilder {

    private String requestToRespondTo;

    public ResponseBuilder(String request){
       requestToRespondTo = request;
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
                          "<br /><input value='submit' type='submit'>";

        if (requestToRespondTo.startsWith("GET /form")){
            body = formBody;
           }
        else{
            body = "<html><body>pong</body></html>";
        }

        response = head + "\n" + contentType + "\n\r\n" + body;
        return response;
    }
}