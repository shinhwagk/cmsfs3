package org.cmsfs.monitors.diskspace;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.StateStoreSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.apache.kafka.streams.state.Stores;
import org.cmsfs.monitors.diskspace.processes.ProcessAlarmPhone;
import org.cmsfs.monitors.diskspace.processes.ProcessAlarmPhoneConfig;
import org.cmsfs.monitors.diskspace.processes.ProcessCollect;
import org.cmsfs.monitors.diskspace.processes.ProcessElasticSearch;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;

public class ProcessorDemo {
    final static ProcessorSupplier<String, String> process_collect = () -> new ProcessCollect();
    final static ProcessorSupplier<String, String> process_elastic_search = () -> new ProcessElasticSearch();
    final static ProcessorSupplier<String, String> process_alarm_phone = () -> new ProcessAlarmPhone();

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
        builder.addProcessor("process-collect", process_collect, "source");

//        builder.addProcessor("process-elastic-search", process_elastic_search, "process-collect");
        builder.addProcessor("process-notice-phone", process_alarm_phone, "process-collect");
        builder.addStateStore(countStore, "process-notice-phone");

        // sink
//        builder.addSink("sink-elastic-search", "elastic-search", "process-elastic-search");
        builder.addSink("sink-notice-phone", "notice-phone", "process-notice-phone");

        KafkaStreams streams = new KafkaStreams(builder, props);

        streams.start();
//        Thread.sleep(50000L);

//        streams.close();
    }

    static StateStoreSupplier countStore = Stores.create("Counts")
            .withStringKeys()
            .withValues(Serdes.serdeFrom(new ttt(), new sss()))
            .persistent()
            .build();
}

class sss implements Deserializer<ProcessAlarmPhoneConfig> {

    private Gson gson = new Gson();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public ProcessAlarmPhoneConfig deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        return gson.fromJson(new String(data), ProcessAlarmPhoneConfig.class);
    }

    @Override
    public void close() {

    }
}

class ttt implements Serializer<ProcessAlarmPhoneConfig> {
    private Gson gson = new Gson();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, ProcessAlarmPhoneConfig data) {
        this.gson.toJson(data).getBytes(Charset.forName("UTF-8"));
        return new byte[0];
    }

    @Override
    public void close() {

    }
}