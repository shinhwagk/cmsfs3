import * as Koa from 'koa';
import * as KoaRouter from 'koa-router';
import * as bodyParser from 'koa-bodyparser'

export const koa = new Koa();
const koaRouter = new KoaRouter();

koa.use(bodyParser({ strict: false }));

koaRouter.post("/v1/error/:monitor", (ctx) => { 
  // ctx.body = "ctx.request.body"
  ctx.status = 200
})

koa.use(koaRouter.routes()).use(koaRouter.allowedMethods());
koa.listen(3002);