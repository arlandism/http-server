package com.arlandis;

import com.arlandis.interfaces.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ServerLogger implements Logger {

    private File logFile;
    private static ServerLogger theInstance;

    private ServerLogger(String filePath) {
        logFile = new File(filePath);
    }

    public void log(String message) {
        try {
            logMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logMessage(String message) throws IOException {
        FileWriter writer = new FileWriter(logFile, true);
        writer.write(message);
        writer.close();
    }

    public static ServerLogger instance(String filePath) {
        if (theInstance == null){
            theInstance = new ServerLogger(filePath);
        }
        return theInstance;
    }
}
