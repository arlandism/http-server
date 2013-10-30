package com.arlandis.interfaces;

public interface Request {

    String getBody();

    String headers();

    Integer bytesToRead();

    Boolean hasBody();

    void setBody(String body);

    String requestedResource();
}
