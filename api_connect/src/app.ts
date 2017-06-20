import * as Koa from 'koa';
import * as KoaRouter from 'koa-router';

import { qJdbcServer } from './appViaQuery'

const koa = new Koa();
const koaRouter = new KoaRouter();

koaRouter
  //.get("/v1/connect/group/jdbc/oracle/:name", async (ctx) => { ctx.body = await query() })
  //.get("/v1/connect/group/jdbc/mysql/:name", )
  //.get("/v1/connect/group/ssh/:name", )
  .get("/v1/connect/jdbc/:kind/:name", qJdbcServer)
  //.get("/v1/connect/ssh/:name", )

koa.use(koaRouter.routes()).use(koaRouter.allowedMethods());
koa.listen(3000);