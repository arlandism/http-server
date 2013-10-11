package com.arlandis;

import com.arlandis.interfaces.NetworkIO;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: arlandislawrence
 * Date: 10/11/13
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */
public interface RequestFactory {
    Request nextRequest(NetworkIO networkIO) throws IOException;
}
