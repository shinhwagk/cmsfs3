const mysql = require('mysql2/promise');

const connection = mysql.createConnection({
  host: 'mysql.cmsfs.org',
  user: 'root',
  password: '123456aA+',
  database: 'cmsfs'
});

export async function queryServer(tab: string) {
  const conn = await connection;
  const [rows, fields] = await conn.execute(`SELECT * FROM ${tab}`);
  return rows
}

export async function queryConfig(tab: string, process: string) {
  const conn = await connection;
  const [rows, fields] = await conn.execute(`SELECT * FROM ${tab} where process = ?`, [process]);
  return rows[0]
}