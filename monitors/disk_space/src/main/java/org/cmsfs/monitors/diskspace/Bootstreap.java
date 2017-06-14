package org.cmsfs.monitors.diskspace;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.List;
import java.util.Properties;

public class Bootstreap {
//    static ScriptExecute s = new ScriptExecute(new ArrayList<>(), new HashMap<>());

    final String name = "diskspace";
    List<String> hosts;

    TopologyBuilder builder = new TopologyBuilder();

    Properties props = new Properties();

    KafkaStreams streams = new KafkaStreams(builder, props);

    public Bootstreap(List<String> hosts) {
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount-processor");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        this.hosts = hosts;
    }

    public void start() {
        this.builder.addSource("source", name);
        this.builder.addProcessor("ssh-collect", new ProcessTest(), "source");

        this.builder.addProcessor("alarm", new ProcessTest(), "ssh-collect");
        this.builder.addProcessor("elasticsearch", new ProcessTest(), "ssh-collect");


        this.builder.addSink("SINK1", "notice_phone", "alarm");
        this.builder.addSink("SINK1", "elasticsearch", "elasticsearch");
        streams.start();
    }
}

class ProcessTest implements ProcessorSupplier {

    @Override
    public Processor get() {
        return new Processor<String, String>() {

            @Override
            public void init(ProcessorContext context) {

            }

            @Override
            public void process(String key, String value) {

            }

            @Override
            public void punctuate(long timestamp) {

            }

            @Override
            public void close() {

            }
        };
    }
}