package com.arlandis;

import com.arlandis.interfaces.FeatureParser;

import java.util.Arrays;

public class CommandLineParser implements FeatureParser {

    private String[] argsToParse;

    public CommandLineParser(String[] args) {
        argsToParse = args;
    }

    public Integer portNum() {
        String port = valueOrDefault("-p", "8000", 1);
        return Integer.parseInt(port);
    }

    private int indexOfFlag(String flag) {
        return Arrays.asList(argsToParse).indexOf(flag);
    }

    public String browsePath() {
        return valueOrDefault("-d", System.getProperty("user.dir"), 1);
    }

    public int servicePort() {
        String port = valueOrDefault("-s", "5000", 1);
        return Integer.parseInt(port);
    }

    @Override
     public Boolean pingValue() {
        String pingValue = valueOrDefault("--ping", "true", 2);
        return Boolean.valueOf(pingValue);
    }

    @Override
     public Boolean formValue() {
        String formValue = valueOrDefault("--form", "true", 2);
        return Boolean.valueOf(formValue);
    }

    @Override
    public Boolean postFormValue() {
        String postFormValue = valueOrDefault("--post-form", "true", 2);
        return Boolean.valueOf(postFormValue);
    }

    @Override
    public Boolean sleepValue() {
        String sleepValue = valueOrDefault("--sleep", "true", 2);
        return Boolean.valueOf(sleepValue);
    }

    @Override
    public Boolean gameValue() {
        String gameValue = valueOrDefault("--game", "true", 2);
        return Boolean.valueOf(gameValue);
    }

    @Override
    public Boolean browseValue() {
        String browseValue = valueOrDefault("--browse", "true", 2);
        return Boolean.valueOf(browseValue);
    }

    private Boolean flagPresent(String flag) {
        final Integer NOT_FOUND = -1;
        return Arrays.asList(argsToParse).indexOf(flag) != NOT_FOUND;
    }

    private String valueOrDefault(String flag, String defaultValue, Integer offSet) {
        String value;
        Integer index = indexOfFlag(flag) + offSet;
        if (flagPresent(flag)) {
            value = argsToParse[index];
        } else {
            value = defaultValue;
        }
        return value;
    }
}
