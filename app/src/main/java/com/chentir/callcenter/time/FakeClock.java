package com.chentir.callcenter.time;

/**
 * Created by a.chentir on 19/02/2017.
 */

public class FakeClock implements Clock {
    private volatile long startTime;
    private volatile int currentTime = 0;

    @Override
    public void start() {
        startTime = currentTime;
    }

    @Override
    public long getElapsedTime() {
        return currentTime - startTime;
    }

    public void advance(long deltaTime) {
        currentTime += deltaTime;
    }
}
