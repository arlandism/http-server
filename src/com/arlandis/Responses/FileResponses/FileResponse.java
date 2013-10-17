package com.arlandis.Responses.FileResponses;

import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.Response;

public abstract class FileResponse implements Response {

    private ResourceRetriever retriever;
    private Request request;

    public FileResponse(Request request, ResourceRetriever retriever){
        this.request = request;
        this.retriever = retriever;
    }

    public String body(){
        return fileBody();
    }

    @Override
    public String contentType() {
        return null;
    }

    private String fileBody() {
        return this.retriever.retrieve(request.requestedResource().trim());
    }

}
