package com.lirik.lagu.app.application;

import android.app.Application;

import com.lirik.lagu.app.infrastructure.LyricClient;
import com.lirik.lagu.app.model.Agent;
import com.lirik.lagu.app.model.User;

/**
 * Created by omayib on 3/23/15.
 */
public class LyricApplication extends Application {
    public User user;

    @Override
    public void onCreate() {
        super.onCreate();

        Agent clientAgent=new LyricClient();
        user=new User(clientAgent);
    }

    public User getUser() {
        return user;
    }
}
