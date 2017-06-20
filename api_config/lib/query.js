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
const mysql = require('mysql2/promise');
const connection = mysql.createConnection({
    host: '10.65.103.75',
    user: 'root',
    password: '123456aA+',
    database: 'cmsfs3'
});
function queryServer(tab) {
    return __awaiter(this, void 0, void 0, function* () {
        const conn = yield connection;
        const [rows, fields] = yield conn.execute(`SELECT * FROM ${tab}`);
        return rows;
    });
}
exports.queryServer = queryServer;
function queryConfig(tab, process) {
    return __awaiter(this, void 0, void 0, function* () {
        const conn = yield connection;
        const [rows, fields] = yield conn.execute(`SELECT * FROM ${tab} where process = ?`, [process]);
        return rows[0];
    });
}
exports.queryConfig = queryConfig;
