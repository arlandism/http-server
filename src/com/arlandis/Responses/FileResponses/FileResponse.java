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
