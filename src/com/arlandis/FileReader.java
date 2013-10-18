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
        f = new File(directoryPath.trim());
        File[] directoryContents = f.listFiles();
        return concatFileNames(directoryContents).toString();
    }

    private StringBuilder concatFileNames(File[] directoryContents) {

        StringBuilder fileNames = new StringBuilder();
        String nextFileName;

        for (int i = 0; i < directoryContents.length; i++){

            nextFileName = directoryContents[i].getName();

            if (notLastFile(directoryContents, i)){
                fileNames.append(nextFileName).append(", ");
            } else {
                fileNames.append(nextFileName);
            }
        }
        return fileNames;
    }

    private Boolean notLastFile(File[] contents, int currentIndex){
        return !(currentIndex == contents.length - 1);
    }

    private String fileData(File f, BufferedReader input) throws IOException {
        char[] data = new char[fileSize(f)];
        input.read(data, 0, fileSize(f));
        return new String(data).trim();
    }

    private Integer fileSize(File f) {
        Long byteLength = f.length();
        return byteLength.intValue();
    }

}
