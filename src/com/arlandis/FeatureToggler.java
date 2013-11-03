package com.arlandis;

import com.arlandis.interfaces.FeatureParser;
import com.arlandis.interfaces.Toggler;

import java.util.HashMap;
import java.util.Map;

public class FeatureToggler implements Toggler {

    private Map<String, Boolean> routeToEnabledValue = new HashMap<String, Boolean>();

    public FeatureToggler(FeatureParser featureParser) {
        routeToEnabledValue.put("GET /ping", featureParser.pingValue());
        routeToEnabledValue.put("GET /form", featureParser.formValue());
        routeToEnabledValue.put("POST /form", featureParser.postFormValue());
        routeToEnabledValue.put("GET /ping?sleep", featureParser.sleepValue());
        routeToEnabledValue.put("GET /game", featureParser.gameValue());
        routeToEnabledValue.put("GET /browse", featureParser.browseValue());
    }

    @Override
    public Boolean isEnabled(String headers) {
        for (String route: routeToEnabledValue.keySet()){
            if (headers.startsWith(route))
                return routeToEnabledValue.get(route);
        }
        return false;
    }
}
