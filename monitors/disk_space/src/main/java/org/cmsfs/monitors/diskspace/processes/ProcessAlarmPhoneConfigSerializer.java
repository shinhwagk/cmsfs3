package org.cmsfs.monitors.diskspace.processes;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.Charset;
import java.util.Map;

public class ProcessAlarmPhoneConfigSerializer implements Serializer<ProcessAlarmPhoneConfig.Config> {
    private Gson gson = new Gson();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, ProcessAlarmPhoneConfig.Config data) {
        this.gson.toJson(data).getBytes(Charset.forName("UTF-8"));
        return new byte[0];
    }

    @Override
    public void close() {

    }
}