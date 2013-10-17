package com.arlandis.interfaces;

public interface ResourceRetriever {
    String retrieve(String toRetrieve);

    String retrieveDirContents(String directoryPath);
}
