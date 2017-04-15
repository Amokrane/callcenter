package com.chentir.callcenter;

/**
 * Created by a.chentir on 18/02/2017.
 */
public class Employee {
    volatile boolean available;
    volatile Call currentCall;

    protected boolean isAvailable() {
        if(currentCall != null && currentCall.hasFinished()) {
            available = true;
            currentCall = null;
        }

        return available;
    }

    public void respondCall(Call call) {
        if(isAvailable()) {
            currentCall = call;
            available = false;
            call.start();
        }
    }
}
