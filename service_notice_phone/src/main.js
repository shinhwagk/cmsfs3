const kafka = require('kafka-node');

// const client = new kafka.Client('zookeeper-server.cmsfs.org:2181');

const topics = [{ topic: 'notice_phone' }];

const options = { autoCommit: true, fetchMaxWaitMs: 1000, fetchMaxBytes: 1024 * 1024 };

const client = new kafka.Client('10.65.103.75:2181');

const consumer = new kafka.HighLevelConsumer(client, topics, options);

consumer.on('message', (message) => {
  console.log(message);
});

consumer.on('error', (err) => {
  console.log('error', err);
});