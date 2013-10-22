package com.arlandis.Responses.FileResponses;

import com.arlandis.Config;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.Response;

import java.io.File;

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
        String dirPath = configPath + requestSection();
        String[] dirContents = retriever.retrieveDirContents(dirPath);
        StringBuilder output = new StringBuilder();
        for (String fileName : dirContents) {
            output.append(fileName).append("<br />");
        }
        return output.toString();
    }

    private String requestSection(){
        Integer browseIndex = request.requestedResource().indexOf("/browse");
        Integer OFF_SET = "/browse".length() + 1;
        return request.requestedResource().substring(browseIndex + OFF_SET);
    }

    @Override
    public String contentType(){
        return "Content-type: text/html";
    }

}
