import json
import http.client

def sendElasticsearch(_index, _type, content):
      headers = {"Content-type": "application/json; charset=utf-8","Authorization":"Basic ZWxhc3RpYzpjaGFuZ2VtZQ=="}
      conn = http.client.HTTPConnection("elasticsearch.cmsfs.org", 9200)
      url = "/%s/%s" % (_index, _type)
      conn.request("POST", url, json.dumps(content), headers)
      response = conn.getresponse()
      print(response.status, response.reason)
      conn.close()