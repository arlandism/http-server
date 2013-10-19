package com.arlandis.Responses.FileResponses;

import com.arlandis.Config;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.Response;

public class FileResponse implements Response {

    private ResourceRetriever retriever;
    private Request request;
    private String contentType;

    public FileResponse(Request request, ResourceRetriever retriever){
        this.request = request;
        this.retriever = retriever;
    }

    public FileResponse(Request request, ResourceRetriever retriever, String contentType){
        this.request = request;
        this.retriever = retriever;
        this.contentType = contentType;

    }

    public String body(){
        return fileBody();
    }

    @Override
    public String contentType() {
        return "Content-type: " + this.contentType;
    }

    private String fileBody() {
        String configRootDir = Config.instance().getRootDir();
        String requestedResource = request.requestedResource();
        String path = configRootDir + requestedResource;
        return retriever.retrieve(path);
    }

}
