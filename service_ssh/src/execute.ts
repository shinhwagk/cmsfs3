import { Client } from 'ssh2';
import { readFileSync } from 'fs';

import { SshMessage } from './message';

async function executeCommandBySsh(sm: SshMessage) {
  const conn = new Client();

  conn.connect({
    host: sm.ip,
    port: sm.port,
    username: sm.user,
    privateKey: readFileSync('/here/is/my/key')
  });

  conn.on('ready', function () {
    console.log('Client :: ready');
    conn.exec(sm.command, function (err, stream) {
      if (err) throw err;
      stream.on('close', function (code, signal) {
        console.log('Stream :: close :: code: ' + code + ', signal: ' + signal);
        conn.end();
      }).on('data', function (data) {
        console.log('STDOUT: ' + data);
      }).stderr.on('data', function (data) {
        console.log('STDERR: ' + data);
      });
    });
  })
}
