import * as Koa from 'koa';
import * as KoaRouter from 'koa-router';

import { qJdbcServer } from './appViaQuery'

export const koa = new Koa();
const koaRouter = new KoaRouter();

koaRouter
  .get("/v1/connect/jdbc/:kind/:name", qJdbcServer)
  //.get("/v1/connect/ssh/:name", )

koa.use(koaRouter.routes()).use(koaRouter.allowedMethods());
//koa.listen(3001);