import mhttp
import json
import time
import mdb
import api_es
from datetime import datetime

while True:
    timestamp = datetime.utcnow().strftime("%Y-%m-%dT%H:%M:%S.%fZ")
    for server in json.loads(mhttp.getServers()):
        try:
          conn = json.loads(mhttp.getServerConnection(server["name"]))
          dbConn = mdb.createDbConn(conn["ip"], str(conn["port"]), conn["service"], conn["user"], conn["password"])
          dbCr = dbConn.cursor()
          dbCr.execute(mdb.monitorSql)
          for username, count in dbCr:
              content = {
                  "username": username,
                  "count": count,
                  "@metric": "sessionNumber",
                  "@dbalias": server["name"],
                  "@timestamp": timestamp
              }
              api_es.sendElasticsearch("monitor", "oracle", content)
          dbCr.close()
          dbConn.close()
        except Exception as inst:
          mhttp.sendError(str(inst))
    time.sleep(60)
