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
const query_1 = require("./query");
exports.qConfig = (ctx) => __awaiter(this, void 0, void 0, function* () {
    const tab = `monitor_${ctx.params.monitor}_config`;
    const process = ctx.params.process;
    ctx.body = yield query_1.queryConfig(tab, process);
});
exports.qServer = (ctx) => __awaiter(this, void 0, void 0, function* () {
    const tab = `monitor_${ctx.params.monitor}_server`;
    ctx.body = yield query_1.queryServer(tab);
});
