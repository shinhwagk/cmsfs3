drop database `monitor-diskSpace`;
create database `monitor-diskSpace`;

use `monitor-diskSpace`;

drop table servers;
create table servers(server varchar(20) primary key);

insert into servers values('yali');

drop table config;
create table config(server varchar(20) primary key, process varchar(20), config json);

-- default config
insert into config values('*', 'p_np', '{"phones": ["13917926210"], "threshold": 90}');


-- configure query;
select
  s.server,
  (select config from config where server = '*' and process = 'p_np') config
from servers s left join (select server, config from config where process = 'p_np') c
on s.server = c.server;