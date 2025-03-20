package org.example;

import java.util.ArrayList;
import java.util.List;

public class ActivityLogger {
    private List<String> activityLog;

    public ActivityLogger() {
        activityLog = new ArrayList<>();
    }

    public void log(String message) {
        activityLog.add(message);
    }

    public List<String> getActivityLog() {
        return activityLog;
    }

    public void clearLog() {
        activityLog.clear();
    }
}
