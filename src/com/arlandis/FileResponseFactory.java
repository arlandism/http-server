package com.arlandis;

import com.arlandis.Responses.FileResponses.PdfFileResponse;
import com.arlandis.Responses.FileResponses.PngFileResponse;
import com.arlandis.Responses.FileResponses.TextFileResponse;
import com.arlandis.Responses.FileResponses.JpegFileResponse;
import com.arlandis.interfaces.Request;
import com.arlandis.interfaces.ResourceRetriever;
import com.arlandis.interfaces.Response;

public class FileResponseFactory {

    public static Response fileResponse(final Request request, final ResourceRetriever retriever) {

        if (isTextFileRequest(request)){
        return new TextFileResponse(request, retriever);
        } else if (isJpegFileRequest(request)) {
            return new JpegFileResponse(request, retriever);
        } else if (isPdfFileRequest(request)){
            return new PdfFileResponse(request, retriever);
        }
          else{
            return new PngFileResponse(request, retriever);
        }
    }

    private static boolean isJpegFileRequest(Request request) {
        return fileRequestSection(request).endsWith("jpeg") ||
                fileRequestSection(request).endsWith("jpg");
    }

    private static boolean isTextFileRequest(Request request) {
        return fileRequestSection(request).endsWith("txt");
    }

    private static boolean isPdfFileRequest(Request request){
        return fileRequestSection(request).endsWith("pdf");
    }

    private static String fileRequestSection(Request request){
        Integer protocolIndex = request.headers().indexOf("HTTP");
        return request.headers().substring(0, protocolIndex).trim();
    }

}
