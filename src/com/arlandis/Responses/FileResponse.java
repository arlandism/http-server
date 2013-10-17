package com.arlandis.Responses;

import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;

public class FileResponse {

    private Request request;
    private ResourceRetriever retriever;

    public FileResponse(Request request, ResourceRetriever retriever){
        this.request = request;
        this.retriever = retriever;
    }

    public String content(){
        return fileBody();
    }

    private String fileBody() {
        Integer startOfFilePath = request.headers().indexOf("browse") + 7;
        Integer endOfFilePath = request.headers().indexOf("HTTP");
        String filePath = request.headers().substring(startOfFilePath, endOfFilePath);
        String data;
        data = addHeaderAndBodyTags(this.retriever.retrieve(filePath.trim()));
        return data;
    }

    private String addHeaderAndBodyTags(String content) {
        return "<html><body>" + content + "</body></html>";
    }
}
