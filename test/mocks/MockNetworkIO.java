package mocks;

import com.arlandis.interfaces.NetworkIO;

import java.io.IOException;
import java.util.LinkedList;

public class MockNetworkIO implements NetworkIO {

    private LinkedList<String> inputQueue = new LinkedList<String>();
    private LinkedList<String> outputQueue = new LinkedList<String>();

    @Override
    public void send(String response) {
        inputQueue.add(response);
    }

    @Override
    public String readLine() throws IOException {
        return outputQueue.removeFirst();
    }

    @Override
    public char[] read(Integer bytesToRead) throws IOException {
        return outputQueue.removeFirst().toCharArray();
    }

    public String lastCallArg(){
        return inputQueue.getLast();
    }

    public void addToOutputQueue(String toAdd){
        outputQueue.add(toAdd);
    }

}
