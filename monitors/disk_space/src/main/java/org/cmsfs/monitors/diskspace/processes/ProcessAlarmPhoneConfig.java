package org.cmsfs.monitors.diskspace.processes;

public class ProcessAlarmPhoneConfig {
    String server;
    Config[] configs;

    class Config {
        String[] phones;
        int threshold;
    }
}