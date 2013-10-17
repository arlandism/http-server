package com.arlandis.Responses.FileResponses;

import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.Response;

public class DirectoryResponse implements Response {

    private Request request;
    private ResourceRetriever retriever;

    public DirectoryResponse(Request request, ResourceRetriever retriever) {
        this.request = request;
        this.retriever = retriever;
    }

    @Override
    public String body() {
        return retriever.retrieveDirContents(requestSection());
    }

    @Override
    public String contentType(){
        return "Content-type: text/html";
    }

    private String requestSection(){
        Integer startOfRequestSection = request.headers().indexOf("/browse") + 8;
        Integer protocolIndex = request.headers().indexOf("HTTP");
        return request.headers().substring(startOfRequestSection, protocolIndex - 1).trim();
    }
}
