export declare function getJdbcConnect(kind: string, name: string): Promise<JdbcConnect>;
export declare function getSshConnect(name: string): Promise<SshConnect>;
export interface JdbcConnect {
    name: string;
    kind: string;
    ip: string;
    port: number;
    protocol: string;
    service: string;
    user: string;
    password: string;
}
export interface SshConnect {
    name: string;
    ip: string;
    port: number;
    user: string;
    password: string;
    privateKey: string;
}
