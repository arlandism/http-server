package com.arlandis.Responses.FileResponses;

import com.arlandis.Responses.FileResponses.FileResponse;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;

public class TextFileResponse extends FileResponse {

    public TextFileResponse(Request request, ResourceRetriever retriever) {
        super(request, retriever);
    }

    @Override
    public String contentType() {
        return "Content-type: text/html";
    }
}
