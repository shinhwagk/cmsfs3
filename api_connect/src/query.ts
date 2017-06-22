const mysql = require('mysql2/promise');

const connection = mysql.createConnection({
  host: 'mysql.cmsfs.org',
  user: 'root',
  password: '123456aA+',
  database: 'cmsfs'
});

export async function queryJdbcServer(kind: string,name:string) {
  const conn = await connection;
  const [rows, fields] = await conn.execute("SELECT * FROM connect_jdbc where kind = ? AND name = ?",[kind,name]);
  return rows[0]
}