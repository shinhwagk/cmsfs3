package org.cmsfs.monitors.diskspace.processes;

import com.google.gson.Gson;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.cmsfs.config.monitor.CmsfsHttpClient;
import org.cmsfs.execute.script.ScriptExecute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProcessElasticSearch implements Processor<String, String> {
    private ProcessorContext context;
    private KeyValueStore<String, Optional<String>> kvStore;

    private List<String> files = new ArrayList(Arrays.asList("format.py", "main_alarm_phone.py"));
    private Gson gson = new Gson();

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.context.schedule(60000);
        this.kvStore = (KeyValueStore<String, Optional<String>>) context.getStateStore("Counts");
    }

    @Override
    public void process(String server, String data) {
        Optional<String> argOpt = this.kvStore.get(server);
        argOpt.ifPresent(arg -> {
            ScriptExecute se = new ScriptExecute(files, Optional.of(data), Optional.of(arg));
            try {
                context.forward(server, se.start());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void punctuate(long timestamp) {
        try {
            String args = CmsfsHttpClient.getMonitorConfig("diskSpace", "p_es");
            for (String arg : gson.fromJson(args, String[].class)) {


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        this.kvStore.get()
    }

    @Override
    public void close() {

    }
}
