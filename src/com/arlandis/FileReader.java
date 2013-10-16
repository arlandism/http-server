package com.arlandis;

import com.arlandis.interfaces.ResourceRetriever;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader implements ResourceRetriever {

    private BufferedReader in;
    private File file;

    public String retrieve(String filePath) {
        String data;
        setFile(filePath.trim());

        try {
            java.io.FileReader reader = new java.io.FileReader(file);
            setInputStream(reader);
            data = fileData();
        } catch (IOException e) {
            e.printStackTrace();
            data = "NOT FOUND";
        }
        return data;
    }

    private Integer fileSize() {
        Long byteLength = file.length();
        return byteLength.intValue();
    }

    private String fileData() throws IOException {
        char[] data = new char[fileSize()];
        in.read(data, 0, fileSize());
        return new String(data).trim();
    }

    private void setFile(String filePath) {
        this.file = new File(filePath.trim());
    }

    public void setInputStream(java.io.FileReader reader) {
        this.in = new BufferedReader(reader);
    }
}
