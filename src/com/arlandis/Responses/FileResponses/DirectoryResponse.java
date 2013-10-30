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
        String dirPath = configPath + resourceSection();
        String[] dirContents = retriever.retrieveDirContents(dirPath);
        StringBuilder output = new StringBuilder();
        for (String fileName : dirContents) {
            output.append(makeLinkTag(fileName));
        }
        return output.toString();
    }

    private String makeLinkTag(String fileName) {
        return "<a href='/browse/" + resourceSection() + fileName + "'>" + fileName + "</a><br />";
    }

    @Override
    public String contentType() {
        return "text/html";
    }

    private String resourceSection(){
        if (requestedResource.contains("browse")){
            return requestedResource.substring(requestedResource.indexOf("browse/") + 7);
        }
        else
            return requestedResource;
    }

}
