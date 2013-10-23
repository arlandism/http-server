package com.arlandis.interfaces;

import java.io.IOException;

public interface RequestFactory {

    Request nextRequest() throws IOException;
}
