package com.arlandis;

import com.arlandis.Responses.FileResponses.BmpFileResponse;
import com.arlandis.Responses.FileResponses.*;
import com.arlandis.interfaces.FileResponseFactory;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.Response;

public class FileResponseFactoryImp implements FileResponseFactory {


    public Response fileResponse(final Request request, final ResourceRetriever retriever) {

        return getResponse(request, retriever);
    }

    private Response getResponse(Request request, ResourceRetriever retriever) {
        if (isTextFileRequest(request)){

        return new TextFileResponse(request, retriever);

        } else if (isJpegFileRequest(request)) {

            return new JpegFileResponse(request, retriever);

        } else if (isPdfFileRequest(request)){

            return new PdfFileResponse(request, retriever);

        } else if (isBmpFileRequest(request)){

            return new BmpFileResponse(request, retriever);

        }
          else if (isPngFileRequest(request)) {

            return new PngFileResponse(request, retriever);

        } else if (isDirectoryRequest(request)){

            return new DirectoryResponse(request, retriever);

        } else {

            return null;

        }
    }

    private boolean isPngFileRequest(Request request) {
        return fileRequestSection(request).endsWith("png");
    }

    private boolean isBmpFileRequest(Request request) {
        return fileRequestSection(request).endsWith("bmp");
    }

    private boolean isJpegFileRequest(Request request) {
        return fileRequestSection(request).endsWith("jpeg") ||
                fileRequestSection(request).endsWith("jpg");
    }

    private boolean isTextFileRequest(Request request) {
        return fileRequestSection(request).endsWith("txt");
    }

    private boolean isPdfFileRequest(Request request){
        return fileRequestSection(request).endsWith("pdf");
    }

    private boolean isDirectoryRequest(Request request){
        return fileRequestSection(request).endsWith("/");
    }

    private String fileRequestSection(Request request){
        return request.requestedResource();
    }
}
