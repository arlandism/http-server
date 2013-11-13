package com.arlandis;

import com.arlandis.interfaces.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ServerLogger implements Logger {

    private File logFile;
    private static ServerLogger theInstance;

    private ServerLogger() {}

    public void log(String message) {
        try {
            logMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void logMessage(String message) throws IOException {
        FileWriter writer = new FileWriter(logFile, true);
        writer.write(message);
        writer.close();
    }

    public static ServerLogger instance() {
        if (theInstance == null){
            theInstance = new ServerLogger();
        }
        return theInstance;
    }

    public void setLogFile(String filePath) {
        this.logFile = new File(filePath);
    }
}
