"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const axios_1 = require("axios");
function getJdbcServers(monitor) {
    return [];
}
exports.getJdbcServers = getJdbcServers;
function getSshServers(monitor) {
    return axios_1.default.get(`http://config.cmsfs.org:3000/v1/monitor/${monitor}/server`).then(rep => rep.data);
}
exports.getSshServers = getSshServers;
