"use strict";
exports.__esModule = true;
var axios_1 = require("axios");
function getJdbcServers(monitor) {
    return [];
}
exports.getJdbcServers = getJdbcServers;
function getSshServers(monitor) {
    return axios_1["default"].get("http://config.cmsfs.org:3000/v1/monitor/" + monitor + "/server").then(function (rep) { return rep.data; });
}
exports.getSshServers = getSshServers;
