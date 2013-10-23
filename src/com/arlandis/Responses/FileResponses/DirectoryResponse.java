package com.arlandis.Responses.FileResponses;

import com.arlandis.Config;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.Response;

public class DirectoryResponse implements Response {

    private String requestedResource;
    private ResourceRetriever retriever;

    public DirectoryResponse(String requestedResource, ResourceRetriever retriever) {
        this.requestedResource = requestedResource;
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
        Integer browseIndex = requestedResource.indexOf("/browse");
        Integer OFF_SET = "/browse".length() + 1;
        return requestedResource.substring(browseIndex + OFF_SET);
    }

    @Override
    public String contentType(){
        return "Content-type: text/html";
    }

}
