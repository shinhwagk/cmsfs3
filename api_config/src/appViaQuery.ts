import { queryServer, queryConfig } from './query'

export const qConfig = async (ctx) => {
  const tab = `monitor_${ctx.params.monitor}_config`
  const process = ctx.params.process
  ctx.body = await queryConfig(tab, process)
}

export const qServer = async (ctx) => {
  const tab = `monitor_${ctx.params.monitor}_server`
  ctx.body = await queryServer(tab)
}