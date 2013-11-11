package mocks;

import com.arlandis.interfaces.Logger;

import java.util.LinkedList;

public class MockLogger implements Logger {

    private LinkedList<String> loggedMessages = new LinkedList<String>();

    public boolean calledWith(String message) {
        return loggedMessages.contains(message);
    }

    @Override
    public void log(String message) {
        loggedMessages.add(message);
    }
}
