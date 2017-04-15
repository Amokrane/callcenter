package com.chentir.callcenter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import com.chentir.callcenter.time.FakeClock;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CallCenterTest {
    private static final long CALL_PERIDOD_1_MS = 100;
    
    /**
     * Assuming there are many respondents per call center
     */
    private final List<Respondent> respondentList = new ArrayList<Respondent>() {{
        add(new Respondent());
        add(new Respondent());
    }};

    /**
     * Assuming there is only one manager per call center
     */
    private final Manager manager = new Manager();

    /**
     * Assuming there is only one director per call center
     */
    private final Director director = new Director();

    private CallCenter callCenter = new CallCenter(respondentList, manager, director);

    private FakeClock fakeClock = new FakeClock();

    @Before
    public void setUp() {
        fakeClock = new FakeClock();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void initiallyAllRespondentAreAvailable() throws Exception {
        /**
         * Initially all employees are available
         */
        assertTrue(manager.isAvailable());
        assertTrue(director.isAvailable());
        respondentList.stream().forEach(r -> assertTrue(r.isAvailable()));
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void respondentsCanHandleTheCalls() throws Exception {
        callCenter.handleIncomingCall(createCall());
        callCenter.handleIncomingCall(createCall());
        assertTrue(manager.isAvailable());
        assertTrue(director.isAvailable());
        respondentList.stream().forEach(r -> assertFalse(r.isAvailable()));
    }


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void respondentsCannotHandleTheCalls() throws Exception {
        callCenter.handleIncomingCall(createCall());
        callCenter.handleIncomingCall(createCall());
        callCenter.handleIncomingCall(createCall());
        assertFalse(manager.isAvailable());
        assertTrue(director.isAvailable());
        respondentList.stream().forEach(r -> assertFalse(r.isAvailable()));
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void onlyDirectorCanHandleTheCalls() throws Exception {
        callCenter.handleIncomingCall(createCall());
        callCenter.handleIncomingCall(createCall());
        callCenter.handleIncomingCall(createCall());
        callCenter.handleIncomingCall(createCall());
        assertFalse(manager.isAvailable());
        respondentList.stream().forEach(r -> assertFalse(r.isAvailable()));
        assertFalse(director.isAvailable());
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void respondentCanHandleTheCallsAfterOneCallHasFinished() throws Exception {
        callCenter.handleIncomingCall(createCall());
        callCenter.handleIncomingCall(createCall());
        fakeClock.advance(CALL_PERIDOD_1_MS + 1);
        assertEquals(2, respondentList.stream().filter(r -> r.isAvailable()).count());
        assertTrue(manager.isAvailable());
        assertTrue(director.isAvailable());
    }


    private Call createCall() {
        return new Call(fakeClock, CALL_PERIDOD_1_MS);
    }
}