"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
const ssh2_1 = require("ssh2");
const fs_1 = require("fs");
function executeCommandBySsh(sm) {
    return __awaiter(this, void 0, void 0, function* () {
        const conn = new ssh2_1.Client();
        conn.connect({
            host: sm.ip,
            port: sm.port,
            username: sm.user,
            privateKey: fs_1.readFileSync('/here/is/my/key')
        });
        conn.on('ready', function () {
            console.log('Client :: ready');
            conn.exec(sm.command, function (err, stream) {
                if (err)
                    throw err;
                stream.on('close', function (code, signal) {
                    console.log('Stream :: close :: code: ' + code + ', signal: ' + signal);
                    conn.end();
                }).on('data', function (data) {
                    console.log('STDOUT: ' + data);
                }).stderr.on('data', function (data) {
                    console.log('STDERR: ' + data);
                });
            });
        });
    });
}
