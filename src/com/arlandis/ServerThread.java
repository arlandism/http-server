package com.arlandis;

import com.arlandis.interfaces.Responder;

public class ServerThread implements Runnable {


    private final Responder responder;

    public ServerThread(Responder responder) {
        this.responder = responder;
    }

    public void run() {
        System.out.printf("New thread spinning - id: %d\n", Thread.currentThread().getId());
        long startTime = System.currentTimeMillis();
        responder.respond();
        System.out.printf("This thread's (id: %d)  execution took: %d milliseconds.\n", Thread.currentThread().getId(),
                          System.currentTimeMillis() - startTime);
    }
}