"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
const Koa = require("koa");
const KoaRouter = require("koa-router");
const query_1 = require("./query");
const koa = new Koa();
const koaRouter = new KoaRouter();
koaRouter
    .get("/v1/connect/group/jdbc/oracle/:name", (ctx) => __awaiter(this, void 0, void 0, function* () { ctx.body = yield query_1.query(); }))
    .get("/v1/connect/group/jdbc/mysql/:name")
    .get("/v1/connect/group/ssh/:name")
    .get("/v1/connect/jdbc/oracle/:name")
    .get("/v1/connect/jdbc/mysql/:name")
    .get("/v1/connect/ssh/:name");
koa.use(koaRouter.routes()).use(koaRouter.allowedMethods());
koa.listen(3000);
