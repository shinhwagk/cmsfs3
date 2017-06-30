"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const axios_1 = require("axios");
function getJdbcConnect(kind, name) {
    return axios_1.default.get(`/v1/connect/jdbc/${kind}/${name}`).then(p => p.data);
}
exports.getJdbcConnect = getJdbcConnect;
function getSshConnect(name) {
    return axios_1.default.get(`/v1/connect/ssh/${name}`).then(rep => rep.data);
}
exports.getSshConnect = getSshConnect;
