package mocks;

import com.arlandis.interfaces.FeatureParser;

public class MockFeatureParser implements FeatureParser {

    private Boolean pingValue;
    private Boolean formValue;
    private Boolean postFormValue;
    private Boolean sleepValue;
    private Boolean gameValue;
    private Boolean browseValue;

    public void setPing(Boolean pingValue){
        this.pingValue = pingValue;
    }

    public void setForm(boolean formValue) {
        this.formValue = formValue;
    }

    public void setPostForm(boolean postFormValue) {
        this.postFormValue = postFormValue;
    }

    public void setSleepValue(boolean sleepValue) {
        this.sleepValue = sleepValue;
    }

    public void setGameValue(Boolean gameValue) {
        this.gameValue = gameValue;
    }

    public void setBrowseValue(Boolean browseValue) {
        this.browseValue = browseValue;
    }

    @Override
    public Boolean pingValue() {
        return pingValue;
    }

    @Override
    public Boolean formValue() {
        return formValue;
    }

    @Override
    public Boolean postFormValue() {
        return postFormValue;
    }

    @Override
    public Boolean sleepValue() {
        return sleepValue;
    }

    @Override
    public Boolean gameValue() {
        return gameValue;
    }

    @Override
    public Boolean browseValue() {
        return browseValue;
    }

}
