package com.arlandis.interfaces;

import com.arlandis.HttpRequest;
import com.arlandis.interfaces.NetworkIO;

import java.io.IOException;

public interface RequestFactory {
    Request nextRequest(NetworkIO networkIO) throws IOException;
}
