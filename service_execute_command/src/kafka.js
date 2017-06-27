"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const kafka = require("kafka-node");
const client = new kafka.Client('zookeeper-server.cmsfs.org:2181');
const producer = new kafka.HighLevelProducer(client);
let kafkaReady = false;
function genPayloads(topic, messages) {
    return [
        { topic: topic, messages: messages }
    ];
}
function parseError(monitor, content) {
    const error = content["error"];
    return `monitor:${monitor} - ${error}`;
}
producer.on('ready', () => kafkaReady = true);
producer.on('error', (err) => console.error('error', err));
function sendKafka(monitor, error) {
    if (kafkaReady) {
        producer.send(genPayloads(monitor, error), (err, data) => {
            if (err) {
                console.error(err);
            }
            else {
                console.log(data);
            }
        });
    }
}
exports.sendKafka = sendKafka;
