const kafka = require('kafka-node');
const request = require('request');

const client = new kafka.Client('zookeeper-server.cmsfs.org:2181');

const topics = [{ topic: 'notice-phone' }];

const options = { autoCommit: true, fetchMaxWaitMs: 1000, fetchMaxBytes: 1024 * 1024 };

const consumer = new kafka.HighLevelConsumer(client, topics, options);

function genOrderNo() {
  const date = new Date()
  const orderNo = date.getTime()
//  [date.getFullYear(), date.getMonth(), date.getDate(),
//  date.getHours(), date.getMinutes(), date.getSeconds(),
//  date.getMilliseconds(), date.getTime()].join("")
//  return Number(orderNo)
  return orderNo
}

function genFormBody(phones, content) {
  return {
    appId: "TOC", orderNo: genOrderNo(), protocol: 'S',
    targetCount: phones.length, targetIdenty: phones.join(";"), content: content, isRealTime: 'true'
  }
}

function consumerMessageEvent(message) {
  try {
    const msg = JSON.parse(message.value)
    const form = genFormBody(msg.phones, msg.content)
    console.info(form)
    console.info(JSON.stringify(form))
    console.info(form.orderNo)
    request.post('http://10.65.209.12:8380/mns-web/services/rest/msgNotify', { form: form }, (err, httpResponse, body) => {
      if (err) { console.error(err) } else { console.info(body) }
    })
  } catch (e) {
    console.error(e);
  }
}

consumer.on('message', consumerMessageEvent);

consumer.on('error', (err) => {
  console.log('error', err);
});