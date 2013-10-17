package com.arlandis;

import com.arlandis.interfaces.ResourceRetriever;
import com.sun.xml.internal.ws.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

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
        f = new File(directoryPath);
        File[] directoryContents = f.listFiles();

        return concatFileNames(directoryContents).toString();
    }

    private StringBuilder concatFileNames(File[] directoryContents) {

        StringBuilder fileNames = new StringBuilder();
        for (int i = 0; i < directoryContents.length; i++){
            if (isEnd(directoryContents, i)){
                fileNames.append(directoryContents[i].getName());
            } else {
            fileNames.append(directoryContents[i].getName()).append("\n");
            }
        }
        return fileNames;
    }

    private Boolean isEnd(File[] contents, int currentIndex){
        return currentIndex == contents.length - 1;
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
