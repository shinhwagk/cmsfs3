import http.client
import json

def httpGetClient(hostname,port,url):
  conn = http.client.HTTPConnection(hostname,port)
  conn.request("GET", url)
  data = conn.getresponse().read().decode('utf-8')
  conn.close()
  return data

def getServers():
  try:
    return httpGetClient("config.cmsfs.org", 3000, "/v1/monitor/sessionNumber/server")
  except:
    return '[]'

def getServerConnection(server):
  return httpGetClient("connect.cmsfs.org", 3000, "/v1/connect/jdbc/oracle/%s" % (server))

def sendError(error):
    headers = {"Content-type": "application/json; charset=utf-8"}
    conn = http.client.HTTPConnection("error.cmsfs.org", 3000)
    url = "/v1/error/sessionNumber"
    content = json.dumps({"error": error})
    conn.request("POST", url, content, headers)
    response = conn.getresponse()
    print(response.status, response.reason)
    conn.close()

if __name__ == "__main__":
  print(getServer())