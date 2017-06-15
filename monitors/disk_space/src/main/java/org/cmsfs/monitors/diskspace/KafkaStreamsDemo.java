package org.cmsfs.monitors.diskspace;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.StateStoreSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.apache.kafka.streams.state.Stores;
import org.cmsfs.kafka.serializer.JsonDeserializer;
import org.cmsfs.kafka.serializer.JsonSerializer;
import org.cmsfs.monitors.diskspace.processes.ProcessAlarmPhone;
import org.cmsfs.monitors.diskspace.processes.ProcessAlarmPhoneConfig;
import org.cmsfs.monitors.diskspace.processes.ProcessCollect;

import java.util.Properties;

public class KafkaStreamsDemo {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, ServiceProperties.APPLICATION_ID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, ServiceProperties.KAFKA_SERVER_URL + ":" + ServiceProperties.KAFKA_SERVER_PORT);
        props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        TopologyBuilder builder = new TopologyBuilder();

        // source
        builder.addSource("source", "monitor-disk-space");

        // process
        builder.addProcessor("process-collect", ProcessCollect::new, "source");

//        builder.addProcessor("process-elastic-search", process_elastic_search, "process-collect");
        builder.addProcessor("process-notice-phone", ProcessAlarmPhone::new, "process-collect");
        builder.addStateStore(countStore, "process-notice-phone");

        // sink
//        builder.addSink("sink-elastic-search", "elastic-search", "process-elastic-search");
        builder.addSink("sink-notice-phone", "notice-phone", "process-notice-phone");

        KafkaStreams streams = new KafkaStreams(builder, props);

        streams.start();
//        Thread.sleep(50000L);

//        streams.close();
    }

    static StateStoreSupplier countStore = Stores.create("ProcessAlarmPhone")
            .withStringKeys()
            .withValues(Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(ProcessAlarmPhoneConfig.Config.class)))
            .persistent()
            .build();
}

