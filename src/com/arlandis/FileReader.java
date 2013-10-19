package com.arlandis;

import com.arlandis.interfaces.ResourceRetriever;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileReader implements ResourceRetriever {

    private File f;

    public String retrieve(String filePath) {
        String returnData;
        f = new File(filePath.trim());

        try {

            returnData = fileData(f);

        } catch (IOException e) {

            e.printStackTrace();
            returnData = "NOT FOUND";

        }
        return returnData;
    }

    @Override
    public String[] retrieveDirContents(String directoryPath) {
        f = new File(directoryPath.trim());
        File[] directoryContents = f.listFiles();
        return concatFileNames(directoryContents);
    }

    private String[] concatFileNames(File[] directoryContents) {

        String[] fileNames = new String[directoryContents.length];
        String nextFileName;

        for (int i = 0; i < directoryContents.length; i++){

            nextFileName = directoryContents[i].getName();
            fileNames[i] = nextFileName;

        }
        return fileNames;
    }

    private Boolean notLastFile(File[] contents, int currentIndex){
        return !(currentIndex == contents.length - 1);
    }

    private String fileData(File f) throws IOException {
        BufferedReader in = new BufferedReader(new java.io.FileReader(f));
        char[] data = new char[fileSize(f)];
        in.read(data, 0, fileSize(f));
        return new String(data).trim();
    }

    private Integer fileSize(File f) {
        Long byteLength = f.length();
        return byteLength.intValue();
    }

}
