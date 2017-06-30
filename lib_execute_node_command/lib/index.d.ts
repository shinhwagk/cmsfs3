import * as ssh2 from 'ssh2';
export interface ConnectConfig extends ssh2.ConnectConfig {
}
export declare function executeCommand(connect: ConnectConfig, command: string): Promise<string>;
