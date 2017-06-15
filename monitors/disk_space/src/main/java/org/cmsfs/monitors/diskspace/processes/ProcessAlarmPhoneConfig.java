package org.cmsfs.monitors.diskspace.processes;

public class ProcessAlarmPhoneConfig {
    String server;
    Config[] configs;

    public class Config {
        String[] phones;
        int threshold;
    }
}