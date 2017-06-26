"use strict";

import { getServers, getConnect, sendError, sendEs, makeEsContent } from './api'
import { executeCommand, connectConfig } from './command'

function boot() {
  const servers = await getServers();
  const timestamp = new Date().toISOString()
  for (server of servers) {
    makeMonitor(server, timetamp).catch(e => sendError(e))
  }
}

async function makeMonitor(server, timestamp) {
  const connect = await getConnect(server)
  const sshConn = connectConfig(connect.host, connect.port, connect.user, undefined, undefined)
  const rsStr = await executeCommand(sshConn)
  const esContent = makeEsContent(JSON.parse(rsStr), server, timestamp)
  sendEs(esContent)
}

setInterval(() => boot(), 1000)