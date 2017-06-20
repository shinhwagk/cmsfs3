const mysql = require('mysql2/promise');

const connection = mysql.createConnection({
  host: '10.65.103.75',
  user: 'root',
  password: '123456aA+',
  database: 'cmsfs3'
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