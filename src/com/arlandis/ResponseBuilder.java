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
                          "<label><input name='foo'>foo</label>" +
                          "<label><input name='bar'>bar</label>";

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
