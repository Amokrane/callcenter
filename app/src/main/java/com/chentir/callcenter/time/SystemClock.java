package com.chentir.callcenter.time;

public class SystemClock implements Clock {

    private volatile long startTime;

    @Override
    public void start() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }
}
