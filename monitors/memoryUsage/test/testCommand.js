const command = require('../lib/command')
const api = require('../lib/api')

const connect = command.connectConfig("10.65.193.29", 22, "root", "oracle", undefined)
command.executeCommand(connect).then(rs => {
  const esContent = api.makeEsContent(JSON.parse(rs), "10.65.193.29", "a")
  console.info(esContent)
}).catch(err => console.info(err))