package org.cmsfs.monitors.diskspace.processes;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import sun.security.krb5.internal.crypto.Des;

import java.util.Map;

public class ProcessAlarmPhoneConfigDeserializer implements Deserializer<ProcessAlarmPhoneConfig.Config> {

    private Gson gson = new Gson();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public ProcessAlarmPhoneConfig.Config deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        return gson.fromJson(new String(data), ProcessAlarmPhoneConfig.Config.class);
    }

    @Override
    public void close() {

    }
}
