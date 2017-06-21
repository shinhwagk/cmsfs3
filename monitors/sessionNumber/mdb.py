import cx_Oracle

monitorSql = "select username, count(*) count from v$session where username is not null group by username"

def createDbConn(ip,port,service,user,ps):
  concUrl = "%s:%s/%s" % (ip,port,service)
  return cx_Oracle.connect(user,ps,concUrl)