package com.arlandis.interfaces;

public interface Request {

    String headers();

    Integer bytesToRead();

    Boolean hasBody();

    void setBody(String body);

    void sleep(Sleeper sleeper);

    String fooValue();

    String barValue();

    String requestedResource();
}
