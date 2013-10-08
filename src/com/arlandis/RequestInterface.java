package com.arlandis;

public interface RequestInterface {

    public String headers();
    public Integer bytesToRead();
    public Boolean hasBody();
    public String getBody();
    public void setBody(String body);
}
