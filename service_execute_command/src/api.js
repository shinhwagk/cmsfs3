"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const KoaRouter = require('koa-router');
const bodyParser = require('koa-bodyparser');
const Koa = require("koa");
exports.koa = new Koa();
const koaRouter = new KoaRouter();
exports.koa.use(bodyParser({ strict: false }));
koaRouter.post("/v1/error/:monitor", (ctx) => {
    sendKafka(ctx.params.monitor, ctx.request.body);
    ctx.status = 200;
});
exports.koa.use(koaRouter.routes()).use(koaRouter.allowedMethods());
