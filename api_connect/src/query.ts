const mysql = require('mysql2/promise');

const connection = mysql.createConnection({
  host: '10.65.103.75',
  user: 'root',
  password: '123456aA+',
  database: 'cmsfs3'
});

export async function queryJdbcServer(kind: string,name:string) {
  const conn = await connection;
  const [rows, fields] = await conn.execute("SELECT * FROM connect_jdbc where kind = ? AND name = ?",[kind,name]);
  return rows[0]
}