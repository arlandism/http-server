package com.arlandis;

import java.util.Arrays;

public class CommandLineParser {

    private String[] argsToParse;

    public CommandLineParser(String[] args) {
        argsToParse = args;
    }

    public Integer portNum() {
        String port = valueOrDefault("-p", "8000");
        return Integer.parseInt(port);
    }

    private int indexOfFlag(String flag) {
        return Arrays.asList(argsToParse).indexOf(flag);
    }

    public String browsePath() {
        return valueOrDefault("-d", System.getProperty("user.dir"));
    }

    private Boolean flagPresent(String flag) {
        final Integer NOT_FOUND = -1;
        return Arrays.asList(argsToParse).indexOf(flag) != NOT_FOUND;
    }

    private String valueOrDefault(String flag, String defaultValue) {
        String value;
        Integer index = indexOfFlag(flag) + 1;
        if (flagPresent(flag)) {
            value = argsToParse[index];
        } else {
            value = defaultValue;
        }
        return value;
    }
}
