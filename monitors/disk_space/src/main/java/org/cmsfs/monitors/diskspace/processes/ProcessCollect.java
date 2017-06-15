package org.cmsfs.monitors.diskspace.processes;

import com.google.gson.Gson;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.cmsfs.config.monitor.CmsfsHttpClient;
import org.cmsfs.execute.script.ScriptExecute;

import java.io.IOException;
import java.util.Optional;


public class ProcessCollect implements Processor<String, String> {
    private ProcessorContext context;
    private String file = "collect.sh";
    private Gson gson = new Gson();

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public void process(String server, String value) {
//        try {
////            String args = CmsfsHttpClient.getMonitorConfig("diskSpace", "p_np", server);
//
////            String[] argsArr = gson.fromJson(argsStr, String[].class);
//
////            for (String args : argsArr) {
////                ScriptExecute se = new ScriptExecute(file, Optional.of(value), Optional.of(args));
////                context.forward(server, se.start());
////            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void punctuate(long timestamp) {
    }

    @Override
    public void close() {
    }
}
