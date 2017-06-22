import mhttp
import json
import time
import mdb
import api_es
from datetime import datetime

while True:
    timestamp = datetime.utcnow().strftime("%Y-%m-%dT%H:%M:%S.%fZ")
    for i in json.loads(mhttp.getServer()):
        servers = json.loads(mhttp.getServerConnection(i["name"]))
        if (len(servers) >= 1):
          try:
            dbConn = mdb.createDbConn(conn["ip1"], str(conn["port"]), conn["service"], conn["user"], conn["password"])
            dbCr = dbConn.cursor()
            dbCr.execute(mdb.monitorSql)
            for username, count in dbCr:
                content = {
                    "username": username,
                    "count": count,
                    "@metric": "sessionNumber",
                    "@dbalias": i["name"],
                    "@timestamp": timestamp
                }
                api_es.sendElasticsearch("monitor", "oracle", content)
            dbCr.close()
            dbConn.close()
          except Exception as inst:
            mhttp.sendError(str(inst))
        else:
          print("null")
    time.sleep(60)
