"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const ssh2 = require("ssh2");
function executeCommand(connect, command) {
    return new Promise((resolve, reject) => {
        console.info(`send execute command for ${connect.host}`);
        var conn = new ssh2.Client();
        // event connect ready.
        conn.on('ready', function () {
            console.log('Client :: ready');
            conn.exec(command, function (err, stream) {
                if (err) {
                    reject(`exec command error: ${err}`);
                }
                else {
                    stream.on('close', function (code, signal) {
                        console.log('Stream :: close :: code: ' + code + ', sig nal: ' + signal);
                        conn.end();
                    }).on('data', function (data) {
                        console.log('STDOUT: ' + data);
                        resolve(data.toString());
                    }).stderr.on('data', function (data) {
                        reject(`exec command stderr error: ${data.toString()}`);
                    });
                }
            });
        }).connect(connect);
        // event connect error.
        conn.on('error', (err) => reject(`connect ssh error: ${err}`));
    });
}
exports.executeCommand = executeCommand;
;
