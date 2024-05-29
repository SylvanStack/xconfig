create table if not exists `configs`
(
    `app`  varchar(64)  not null,
    `env`  varchar(64)  not null,
    `ns`   varchar(64)  not null,
    `pkey` varchar(64)  not null,
    `pval` varchar(128) null
);

insert into configs (app, env, ns, pkey, pval)
values ('app1', 'dev', 'public', 'x.a', 'dev100');
insert into configs (app, env, ns, pkey, pval)
values ('app1', 'dev', 'public', 'x.b', 'http://localhost:9129');
insert into configs (app, env, ns, pkey, pval)
values ('app1', 'dev', 'public', 'x.c', 'cc100');

create table if not exists `locks`
(
    `id`  int primary key not null,
    `app` varchar(64)     not null
);
insert into locks (id, app)
values (1, 'xconfig-server');


