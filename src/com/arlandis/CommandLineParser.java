package com.arlandis;

import com.arlandis.interfaces.FeatureParser;

import java.util.Arrays;

public class CommandLineParser implements FeatureParser {

    private String[] argsToParse;

    public CommandLineParser(String[] args) {
        argsToParse = args;
    }

    public Integer portNum() {
        String port = parseShortFormFlag("-p", "8000");
        return Integer.parseInt(port);
    }

    private int indexOfFlag(String flag) {
        return Arrays.asList(argsToParse).indexOf(flag);
    }

    public String browsePath() {
        return parseShortFormFlag("-d", System.getProperty("user.dir"));
    }

    public int servicePort() {
        String port = parseShortFormFlag("-s", "5000");
        return Integer.parseInt(port);
    }

    @Override
     public Boolean pingValue() {
        return parseLongFormFlag("--ping");
    }

    @Override
     public Boolean formValue() {
        return parseLongFormFlag("--form");
    }

    @Override
    public Boolean postFormValue() {
        return parseLongFormFlag("--post-form");
    }

    @Override
    public Boolean sleepValue() {
        return parseLongFormFlag("--sleep");
    }

    @Override
    public Boolean gameValue() {
        return parseLongFormFlag("--game");
    }

    @Override
    public Boolean browseValue() {
        return parseLongFormFlag("--browse");
    }

    private Boolean parseLongFormFlag(String flag){
        for (String arg: argsToParse){
            if (arg.startsWith(flag)){
                Integer indexOfEqualSign = arg.indexOf("=");
                return Boolean.valueOf(arg.substring(indexOfEqualSign + 1));
            }
        }
        return true;
    }

    private String parseShortFormFlag(String flag, String defaultValue) {
        String value;
        Integer index = indexOfFlag(flag) + 1;
        if (flagPresent(flag)) {
            value = argsToParse[index];
        } else {
            value = defaultValue;
        }
        return value;
    }

    private Boolean flagPresent(String flag) {
        final Integer NOT_FOUND = -1;
        return Arrays.asList(argsToParse).indexOf(flag) != NOT_FOUND;
    }


}
