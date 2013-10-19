package com.arlandis.Responses.FileResponses;

import com.arlandis.Config;
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
        String configPath = Config.instance().getRootDir();
        String dirPath = configPath + request.requestedResource();
        String[] dirContents = retriever.retrieveDirContents(dirPath);
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < dirContents.length ; i++){
            String file = dirContents[i];
            output.append("<a href='").append(file).append("'>").append(file).append("</a>").append(" ");
        }
        return output.toString();
    }

    @Override
    public String contentType(){
        return "Content-type: text/html";
    }

}
