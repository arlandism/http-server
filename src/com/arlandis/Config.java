package com.arlandis;

public class Config {

    private int portNum;
    private static Config theInstance;
    private String rootDir;

    public static Config instance() {
        if (theInstance == null){
            theInstance = new Config();
        }
        return theInstance;
    }

    public void setPortNum(int portNum) {
        this.portNum = portNum;
    }

    public int getPortNum() {
        return portNum;
    }

    public String getRootDir() {
        return rootDir + "/";
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    private Config(){}
}
