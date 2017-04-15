package com.chentir.callcenter;

import com.chentir.callcenter.time.Clock;

/**
 * Created by a.chentir on 19/02/2017.
 */

public class Call {
    private final long callPeriod;
    private final Clock clock;

    public Call(Clock clock, long callPeriod) {
        this.callPeriod = callPeriod;
        this.clock = clock;
    }

    public void start() {
        clock.start();
    }

    public boolean hasFinished() {
        return clock.getElapsedTime() > callPeriod;
    }
}
