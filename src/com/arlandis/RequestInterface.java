package com.arlandis;

public interface RequestInterface {

    public String headers();
    public Integer bytesToRead();
    public Boolean hasBody();
    public void setBody(String body);

    String fooValue();
    String barValue();
}
