package com.arlandis;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileReader {

    private BufferedReader in;
    private File file;

    public String retrieve(String filePath) throws IOException {
        in = new BufferedReader(new java.io.FileReader(filePath));
        setFile(filePath);
        return fileData();
    }

    private Integer fileSize(){
        Long byteLength = file.length();
        return byteLength.intValue();
    }

    private String fileData() throws IOException {
        char[] data = new char[fileSize()];
        in.read(data);
        return new String(data).trim();
    }

    private void setFile(String filePath){
        this.file = new File(filePath);
    }

}
