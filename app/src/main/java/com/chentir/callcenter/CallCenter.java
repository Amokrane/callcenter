package com.chentir.callcenter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import java.util.List;
import java.util.Optional;

/**
 * Created by a.chentir on 18/02/2017.
 */
public class CallCenter {
    private final List<Respondent> respondentList;
    private final Manager manager;
    private final Director director;

    public CallCenter(List<Respondent> respondentList, Manager manager, Director director) {
        this.respondentList = respondentList;
        this.manager = manager;
        this.director = director;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void handleIncomingCall(Call call) {
        Optional<Respondent> respondent = respondentList.stream().filter(r -> r.isAvailable()).findFirst();

        if(respondent.isPresent()) {
            respondent.get().respondCall(call);
        } else if (manager.isAvailable()) {
            manager.respondCall(call);
        } else {
            // What happens if the director is already busy answering a call?
            director.respondCall(call);
        }
    }
}
