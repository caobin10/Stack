package com.demo.test.manager;

import android.app.Activity;

import java.util.Stack;

public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
            activityStack = new Stack<Activity>();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    public void finishActivity() {
        finishActivity(activityStack.lastElement());
    }

    public void finishActivity(Activity activity) {
        activityStack.remove(activity);
        activity.finish();
        activity = null;
    }
}
