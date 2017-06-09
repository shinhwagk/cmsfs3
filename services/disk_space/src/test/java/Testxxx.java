import org.junit.Before;
import org.junit.Test;
import org.monitor.cmsfs.kafka.KafkaLocalServer;

public class Testxxx {
    @Before
    public void abc() {
        KafkaLocalServer.apply(true).start();
    }

    @Test
    public void xxx() {
        System.out.println("xxx");
    }
}
