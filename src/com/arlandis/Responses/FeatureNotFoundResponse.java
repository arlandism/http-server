package com.arlandis.Responses;

import com.arlandis.interfaces.Response;

public class FeatureNotFoundResponse implements Response {

    @Override
    public String body() {
        return "<html><body>The feature you're looking for can't be found.</body></html>";
    }

    @Override
    public String contentType() {
        return "Content-type: text/html";
    }
}
