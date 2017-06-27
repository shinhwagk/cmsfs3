"use strict";

const axios = require("axios");

exports.getServers = function () {
  const url = "http://config.cmsfs.org:3000/v1/monitor/memgoryUsage/server";
  return axios.get(url)
}

exports.getConnect = function (server) {
  const url = `http://connect.cmsfs.org:3000/v1/connect/ssh/${server}`;
  return axios.get(url)
}

exports.sendError = function (error) {
  axios.post('http://error.cmsfs.org:3000/v1/error/memgoryUsage', {
    phones: ["13917926210"],
    error: error
  }).then((response) => console.log(response))
    .catch((error) => console.log(error));
}

exports.sendEs = function (_index, _type, content) {
  const url = `http://elasticsearch.cmsfs.org:9200/${_index}/${_type}`;
  axios.post(url, content)
    .then((response) => console.log(response))
    .catch((error) => sendError(`send es error: ${error}}`));
}

exports.makeEsContent = function (rs, host, timestamp) {
  return {
    "total": rs[0],
    "free": rs[1],
    "@metric": "memoryUsage",
    "@host": host,
    "@timestamp": timestamp
  }
}