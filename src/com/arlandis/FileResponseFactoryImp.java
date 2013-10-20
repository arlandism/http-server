package com.arlandis;

import com.arlandis.Responses.FileResponses.*;
import com.arlandis.interfaces.FileResponseFactory;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileResponseFactoryImp implements FileResponseFactory {


    public Response fileResponse(final Request request, final ResourceRetriever retriever) {

        return getResponse(request, retriever);
    }

    private Map<String, String> endingToContentType(){
        Map<String, String> endingToContentType = new HashMap<String, String>();
        endingToContentType.put("txt", "text/html");
        endingToContentType.put("pdf", "application/pdf");
        endingToContentType.put("bmp", "image/bmp");
        endingToContentType.put("png", "image/png");
        endingToContentType.put("jpg", "image/jpeg");
        endingToContentType.put("jpeg", "image/jpeg");

        return endingToContentType;
    }

    private Response getResponse(Request request, ResourceRetriever retriever) {

        String requestSection = fileRequestSection(request);
        Response response;

        if(isDirectoryRequest(request)){
            response = new DirectoryResponse(request, retriever);
        }else{
            String contentType = lookupContentType(requestSection);
            response = new FileResponse(request, retriever, contentType);
        }

        return response;
    }

    private boolean isDirectoryRequest(Request request){
        return (new File(request.requestedResource())).isDirectory();
    }

    private String lookupContentType(String requestSection) {
        String contentType = endingToContentType().get(extension(requestSection));
        if (!(contentType == null)){
            return contentType;
        } else {
            return "text/html";
        }
    }

    private String extension(String filename){
        int lastDotIndex = filename.lastIndexOf('.');

        return filename.substring(lastDotIndex + 1);
    }

    private String fileRequestSection(Request request){
        return request.requestedResource();
    }
}
