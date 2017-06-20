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
    host: 'mysql.cmsfs.org',
    user: 'root',
    password: '123456aA+',
    database: 'cmsfs3'
});
function query() {
    return __awaiter(this, void 0, void 0, function* () {
        const conn = yield connection;
        const [rows, fields] = yield conn.execute("SELECT connects FROM connect_group_jdbc where name ='diskSpace'");
        return rows[0];
    });
}
exports.query = query;
