package com.arlandis.Responses;

import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.Response;

public class FileResponse implements Response {

    private Request request;
    private ResourceRetriever retriever;

    public FileResponse(Request request, ResourceRetriever retriever){
        this.request = request;
        this.retriever = retriever;
    }

    public String body(){
        return fileBody();
    }

    @Override
    public String contentType() {
        return "Content-type: text/html";
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
