const kafka = require('kafka-node'),

const client = new kafka.Client('zookeeper-server.cmsfs.org:2181');

const consumer = new kafka.HighLevelConsumer(client, topics, options);

const options = { autoCommit: true, fetchMaxWaitMs: 1000, fetchMaxBytes: 1024 * 1024 };

const topic = argv.topic || 'notice_phone';

consumer.on('message', (message) => {
  console.log(message);
});

consumer.on('error', (err) => {
  console.log('error', err);
});