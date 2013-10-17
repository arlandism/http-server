package com.arlandis.Responses.FileResponses;

import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;

public class PdfFileResponse extends FileResponse {

    public PdfFileResponse(Request request, ResourceRetriever retriever) {
        super(request, retriever);
    }

    public String contentType(){
        return "Content-type: application/pdf";
    }
}
