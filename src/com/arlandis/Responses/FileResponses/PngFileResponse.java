package com.arlandis.Responses.FileResponses;

import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;

public class PngFileResponse extends FileResponse {

    public PngFileResponse(Request request, ResourceRetriever retriever) {
        super(request, retriever);
    }

    @Override
    public String contentType() {
        return "Content-type: image/png";
    }

}
