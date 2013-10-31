package com.arlandis;

import com.arlandis.Responses.*;
import com.arlandis.interfaces.*;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseBuilder implements ResponseBuilder {

    private ResourceRetriever retriever;
    private FileResponseFactory fileResponseFactory;
    private TTTService service;
    private Map<String, Response> requestHeaderToResponse = new HashMap<String, Response>();

    public HttpResponseBuilder(ResourceRetriever retriever, FileResponseFactory factory, TTTService service) {
        this.retriever = retriever;
        this.fileResponseFactory = factory;
        this.service = service;
    }

    @Override
    public String generateResponse(Request request) {
        Response response;
        requestHeaderToResponse.put("GET /ping", new PongResponse());
        requestHeaderToResponse.put("GET /form", new GetFormResponse());
        requestHeaderToResponse.put("POST /form", new PostFormResponse(request));
        requestHeaderToResponse.put("GET /ping?sleep", new SleepResponse(request, new ThreadSleeper()));
        requestHeaderToResponse.put("GET /game", new ServiceResponse(service, request));
        response = findResponse(requestHeaderToResponse, request);

        return "HTTP/1.0 200 OK" + "\r\n" + "Content-type: " + response.contentType() + "\r\n\r\n" + response.body();
    }

    private Response findResponse(Map<String, Response> registeredHeaders, Request request){

      for (String route: registeredHeaders.keySet()){
          if (request.headers().startsWith(route))
              return registeredHeaders.get(route);
      }

      if (isResourceRequest(request)){
          return fileResponseFactory.fileResponse(request, retriever);
      }

      return new FeatureNotFoundResponse();
    }

    private boolean isResourceRequest(Request request) {
        return request.headers().startsWith("GET /browse");
    }
}