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

public class ProcessAlarmPhone implements Processor<String, String> {
    private ProcessorContext context;
    private KeyValueStore<String, Optional<ProcessAlarmPhoneConfig.Config[]>> kvStore;

    private List<String> files = new ArrayList(Arrays.asList("format.py", "main_alarm_phone.py"));
    private Gson gson;

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.context.schedule(60000);
        this.kvStore = (KeyValueStore<String, Optional<ProcessAlarmPhoneConfig.Config[]>>) context.getStateStore("Counts");
    }

    @Override
    public void process(String server, String data) {
        Optional<ProcessAlarmPhoneConfig.Config[]> configsOpt = this.kvStore.get(server);
        configsOpt.ifPresent(configs -> {
            for (ProcessAlarmPhoneConfig.Config config : configs) {
                ScriptExecute se = new ScriptExecute(files, Optional.of(data), Optional.of(gson.toJson(config)));
                try {
                    context.forward(server, se.start());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void punctuate(long timestamp) {
        try {
            String args = CmsfsHttpClient.getMonitorConfig("diskSpace", "p_np");
            for (ProcessAlarmPhoneConfig papc : this.gson.fromJson(args, ProcessAlarmPhoneConfig[].class)) {
                this.kvStore.put(papc.server, Optional.of(papc.configs));
            }
            this.context.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {

    }
}

