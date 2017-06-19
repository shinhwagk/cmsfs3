import * as Koa from 'koa';
import * as KoaRouter from 'koa-router';

const koa = new Koa();
const koaRouter = new KoaRouter();

koaRouter
  .get("/v1/connect/group/jdbc/oracle/:name", xxx)
  .get("/v1/connect/group/jdbc/mysql/:name")
  .get("/v1/connect/group/ssh/:name")
  .get("/v1/connect/jdbc/oracle/:name")
  .get("/v1/connect/jdbc/mysql/:name")
  .get("/v1/connect/ssh/:name")