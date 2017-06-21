import { queryJdbcServer } from './query'

export const qJdbcServer = async (ctx) => {
  const kind = ctx.params.kind
  const name = ctx.params.name
  ctx.body = await queryJdbcServer(kind,name) || '[]'
}