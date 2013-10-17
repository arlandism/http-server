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
        String dirPath = request.requestedResource();
        return retriever.retrieveDirContents(dirPath);
    }

    @Override
    public String contentType(){
        return "Content-type: text/html";
    }

}
