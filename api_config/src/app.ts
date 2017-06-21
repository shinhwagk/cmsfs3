import * as Koa from 'koa';
import * as KoaRouter from 'koa-router';

import { qServer, qConfig } from './appViaQuery'

export const koa = new Koa();
const koaRouter = new KoaRouter();

koaRouter
  .get("/v1/monitor/:monitor/config/:process", qConfig)
  .get("/v1/monitor/:monitor/server", qServer)

koa.use(koaRouter.routes()).use(koaRouter.allowedMethods());
// koa.listen(3000);