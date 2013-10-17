package com.arlandis;

import com.arlandis.interfaces.ResourceRetriever;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileReader implements ResourceRetriever {

    private File f;
    private BufferedReader inputStream;

    public String retrieve(String filePath) {
        String returnData;
        f = new File(filePath.trim());

        try {
            java.io.FileReader reader = new java.io.FileReader(f);
            inputStream = new BufferedReader(reader);
            returnData = fileData(f, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            returnData = "NOT FOUND";
        }
        return returnData;
    }

    @Override
    public String retrieveDirContents(String directoryPath) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private Integer fileSize(File f) {
        Long byteLength = f.length();
        return byteLength.intValue();
    }

    private String fileData(File f, BufferedReader in) throws IOException {
        char[] data = new char[fileSize(f)];
        in.read(data, 0, fileSize(f));
        return new String(data).trim();
    }

}
