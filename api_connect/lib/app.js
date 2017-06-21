"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const Koa = require("koa");
const KoaRouter = require("koa-router");
const appViaQuery_1 = require("./appViaQuery");
const koa = new Koa();
const koaRouter = new KoaRouter();
koaRouter
    .get("/v1/connect/jdbc/:kind/:name", appViaQuery_1.qJdbcServer);
//.get("/v1/connect/ssh/:name", )
koa.use(koaRouter.routes()).use(koaRouter.allowedMethods());
koa.listen(3001);
