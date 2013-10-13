package com.arlandis.interfaces;

public interface Request {

    public String headers();

    public Integer bytesToRead();

    public Boolean hasBody();

    public void setBody(String body);

    public void sleep(Sleeper sleeper);

    String fooValue();

    String barValue();
}
