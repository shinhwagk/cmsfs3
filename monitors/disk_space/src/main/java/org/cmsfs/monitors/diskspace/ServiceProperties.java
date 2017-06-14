package org.cmsfs.monitors.diskspace;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ServiceProperties {
//    Properties p = new Properties(new FileInputStream(new File("/servers.properties")));


    public static final String TOPIC = "monitor-disk-space";
    public static final String KAFKA_SERVER_URL = "localhost";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final String TOPIC2 = "topic2";
    public static final String TOPIC3 = "topic3";
    public static final String APPLICATION_ID = "kafka.stream.application.id";
    public static final String SOURCE_TOPIC = "kafka.stream.topic.source";
    public static final String SINK_TOPIC = "kafka.stream.topic.sink";

    private ServiceProperties() {}
}
