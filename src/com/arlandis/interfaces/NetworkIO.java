package com.arlandis.interfaces;

import java.io.IOException;

public interface NetworkIO {

    void send(String response);

    String readLine() throws IOException;

    char[] read(Integer bytesToRead) throws IOException;

}
