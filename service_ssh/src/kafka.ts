import * as kafka from 'kafka-node';

const client = new kafka.Client('zookeeper-server.cmsfs.org:2181');
const producer = new kafka.HighLevelProducer(client);

let kafkaReady: boolean = false;

function genPayloads(topic: string, messages: string) {
  return [
    { topic: topic, messages: messages }
  ];
}

function parseError(monitor, content): string {
  const error = content["error"]
  return `monitor:${monitor} - ${error}`
}

producer.on('ready', () => kafkaReady = true);
producer.on('error', (err) => console.error('error', err));

export function sendKafka(monitor, error) {
  if (kafkaReady) {
    producer.send(genPayloads(monitor, error), (err, data) => {
      if (err) { console.error(err); } else { console.log(data); }
    });
  }
}