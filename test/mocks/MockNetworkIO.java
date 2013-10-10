package mocks;

import com.arlandis.interfaces.NetworkIO;
import com.arlandis.interfaces.RequestInterface;

import java.io.IOException;
import java.util.LinkedList;

public class MockNetworkIO implements NetworkIO {

    private LinkedList requestQueue = new LinkedList();
    private LinkedList responseQueue = new LinkedList();

    @Override
    public void send(String response) {
        responseQueue.add(response);
    }

    @Override
    public String readLine() throws IOException {
        return null;
    }

    @Override
    public char[] read(Integer bytesToRead) throws IOException {
        return new char[0];
    }

    @Override
    public RequestInterface nextRequest() {
        return (RequestInterface) requestQueue.getLast();
    }

    public String lastResponse(){
        return (String) responseQueue.getLast();
    }

    public void addToRequestQueue(RequestInterface request){
        requestQueue.add(request);
    }
}
