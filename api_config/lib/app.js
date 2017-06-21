"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const Koa = require("koa");
const KoaRouter = require("koa-router");
const appViaQuery_1 = require("./appViaQuery");
exports.koa = new Koa();
const koaRouter = new KoaRouter();
koaRouter
    .get("/v1/monitor/:monitor/config/:process", appViaQuery_1.qConfig)
    .get("/v1/monitor/:monitor/server", appViaQuery_1.qServer);
exports.koa.use(koaRouter.routes()).use(koaRouter.allowedMethods());
// koa.listen(3000); 
