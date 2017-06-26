// 
"use strict";

const ssh2 = require('ssh2');

// monitor command.
// const mCommand = `free | sed -n "2p" | awk '{print $2 "\t" $4 + $6 }'`
// data template {"total": 0000, "free" :111}
const mCommand = `free | sed -n "2p" | awk '{print "[" $2 "," $4 + $6 "]" }'`

exports.executeCommand = function (connect) {
  return new Promise((resolve, reject) => {
    console.info(`send execute command for ${connect.host}`)
    var conn = new ssh2.Client();
    // event connect ready.
    conn.on('ready', function () {
      console.log('Client :: ready');
      conn.exec(mCommand, function (err, stream) {
        if (err) { reject(`exec command error: ${err}`); }
        else {
          stream.on('close', function (code, signal) {
            console.log('Stream :: close :: code: ' + code + ', signal: ' + signal);
            conn.end();
          }).on('data', function (data) {
            console.log('STDOUT: ' + data);
            resolve(data.toString())
          }).stderr.on('data', function (data) {
            reject(`exec command stderr error: ${data.toString()}`);
          });
        }
      });
    }).connect(connect);

    // event connect error.
    conn.on('error', (err) => reject(`connect ssh error: ${err}`))
  })
};

exports.connectConfig = function (host, port, user, password, privateKey) {
  if (password === undefined) {
    return {
      host: host,
      port: port,
      username: user,
      privateKey: require('fs').readFileSync('~/.ssh/id_rsa')
    }
  } else {
    return {
      host: host,
      port: port,
      username: user,
      password: password
    }
  }
}