export declare function getJdbcServers(monitor: string): JdbcServer[];
export declare function getSshServers(monitor: string): Promise<SshServer[]>;
export interface JdbcServer {
    kind: string;
    name: string;
}
export interface SshServer {
    name: string;
}
