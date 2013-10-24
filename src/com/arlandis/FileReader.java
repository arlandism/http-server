package com.arlandis;

import com.arlandis.interfaces.ResourceRetriever;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileReader implements ResourceRetriever {

    private File f;

    public String retrieve(String filePath) {
        String fileData;
        f = new File(filePath.trim());

        try {

            fileData = fileData(f);

        } catch (IOException e) {

            e.printStackTrace();
            fileData = "NOT FOUND";

        }
        return fileData;
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
            File nextFile = directoryContents[i];
            nextFileName = nextFile.getName();
            if (nextFile.isDirectory()){
                fileNames[i] = nextFileName + "/";
            } else {
                fileNames[i] = nextFileName;
            }

        }
        return fileNames;
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
