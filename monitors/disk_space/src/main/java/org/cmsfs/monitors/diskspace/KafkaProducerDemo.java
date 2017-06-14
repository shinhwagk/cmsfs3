package org.cmsfs.monitors.diskspace;

public class KafkaProducerDemo {
    public static void main(String[] args) {
        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
        Producer producerThread = new Producer(ServiceProperties.TOPIC, isAsync);
        producerThread.start();
    }
}
