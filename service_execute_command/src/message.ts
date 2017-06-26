export interface SshMessage {
  ip: string,
  port: number,
  user: string,
  password?: string,
  privateKey?: string,
  command: string,
  topic:string[]
}