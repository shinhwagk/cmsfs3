import * as kafka from 'kafka-node';
const HighLevelProducer = kafka.HighLevelProducer;
const client = new kafka.Client('zookeeper-server.cmsfs.org:2181');
const producer = new HighLevelProducer(client);

function genPayloads(content: string) {
  return [
    { topic: 'notice-phone', messages: JSON.stringify({ phones: ['13917926210'], content: content }) }
  ];
}

let kapi: KafkaApi;

producer.on('ready', () => {
  kapi = new KafkaApi(producer);

});

producer.on('error', (err) => console.error('error', err));


class KafkaApi {
  constructor(p: kafka.HighLevelProducer) { }
}