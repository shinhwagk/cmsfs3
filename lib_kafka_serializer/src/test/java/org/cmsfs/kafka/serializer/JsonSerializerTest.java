package org.cmsfs.kafka.serializer;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class JsonSerializerTest {

    final String json = "{\"phones\":[\"xxx\"],\"threshold\":100}".trim();

    @Test
    public void deSerializerTest() {
        JsonDeserializer<TestClass> d = new JsonDeserializer<>(TestClass.class);
        TestClass config = d.deserialize("", json.getBytes(StandardCharsets.UTF_8));
        assertEquals(config.phones[0], "xxx");
    }

    @Test
    public void serializerTest() {
        JsonSerializer<TestClass> s = new JsonSerializer<>();
        TestClass config = new TestClass(new String[]{"xxx"}, 100);
        byte[] bytes = s.serialize("", config);
        String json = new String(bytes, StandardCharsets.UTF_8);

        assertEquals(json, this.json);
    }
}
