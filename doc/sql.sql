# 项目表
create table projects
(
    `id`                  varchar(30) not null,
    `project_name`        varchar(30) not null unique comment '项目名称',
    `project_description` text comment '项目介绍',
    `create_user`         varchar(30) comment '创建用户',
    `is_delete`           tinyint   default 0 comment '默认值：0 ，已删除：-1',
    `create_time`         timestamp default current_timestamp comment '创建时间',
    `update_time`         timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`id`)
) comment '项目表';

# 构建时间配置表
create table build_time
(
    `id`             varchar(30) not null,
    `project_id`     varchar(30) not null unique,
    `time_unit`      varchar(10) not null default 'day' comment 'second  秒 ,minute 分 ,hour  时 ,day 日 ,week 周 ,month 月 ,year 年',
    `first_run_time` timestamp   not null comment '首次运行时间',
    primary key (`id`)
) comment '构建时间配置表';


# 数据库配置表
create table db_config
(
    `id`       varchar(30) not null,
    `db_type`  varchar(10) not null comment '数据库类型，例如：mysql',
    `name`     varchar(20) not null comment '配置命名',
    `host`     varchar(30) not null comment '数据库 host',
    `username` varchar(30) not null comment '登录用户',
    `password` varchar(30) comment '登录密码',
    `database` varchar(20) not null comment '需要连接的数据库',
    `port`     varchar(20) not null comment '连接端口',
    primary key (`id`)
) comment '数据库配置表';


# 模块表
create table project_modules
(
    `id`                 varchar(30) not null,
    `project_id`         varchar(30) not null,
    `parent_id`          varchar(30) comment '为 null 则为一级模块',
    `indexs`              tinyint     not null comment '模块排序',
    `module_name`        varchar(30) not null comment '模块名称',
    `module_description` text comment '模块描述',
    `is_delete`          tinyint   default 0 comment '默认值：0 ，已删除：-1',
    `create_time`        timestamp default current_timestamp comment '创建时间',
    `update_time`        timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`id`)
) comment '模块表';


# 用例表
create table test_case
(
    `id`          varchar(30) not null,
    `module_id`   varchar(30) not null,
    `case_name`   varchar(30) not null comment 'case 名称',
    `index`       int         not null comment '记录 case 运行的顺序',
    `is_delete`   tinyint   default 0 comment '默认值：0 ，已删除：-1',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `update_time` timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (`id`)
) comment '用例表';


# 用例详情表
create table case_info
(
    `id`              varchar(30)  not null,
    `case_id`         varchar(30)  not null unique,
    `request_url`     varchar(252) not null comment '请求 url',
    `request_method`  varchar(10)  not null comment '请求方法',
    `request_body`    text comment '请求体',
    `request_headers` varchar(252) comment '请求头',
    `check_result`    varchar(252) comment '校验结果',
    primary key (`id`)
) comment '用例详情表';


# 运行结果存储表
create table run_result
(
    `id`                varchar(30)  not null,
    `case_id`           varchar(30)  not null,
    `run_time`          timestamp    not null comment '运行时间',
    `request_url`       varchar(252) not null comment '请求 url',
    `request_method`    varchar(10)  not null comment '请求方法',
    `request_headers`   varchar(252) comment '请求头',
    `request_body`      text comment '请求体',
    `status_code`       int          not null comment '响应状态码',
    `response_body`     text comment '响应文本',
    `exception_message` text comment '异常消息',
    `check_result`      varchar(252) comment '校验结果',
    `create_time`       timestamp default current_timestamp comment '创建时间',
    primary key (`id`)
) comment '运行结果存储表';


# 依赖用例表
create table depend_case
(
    `id`            varchar(30)  not null,
    `case_id`       varchar(30)  not null,
    `depend_case`   varchar(30)  not null comment '依赖case的id',
    `depend_params` varchar(252) not null comment 'map 转字符串存储，{"username":"$.username"}  value 放的是 jsonpath 表达式  或  正则',
    `operation`     tinyint default 0 comment '默认 0，case 执行前 ， -1  case执行结束后执行 ',
    primary key (`id`)
) comment '依赖用例表';


# 数据库操作表
create table db_operation
(
    `id`           varchar(30) not null,
    `case_id`      varchar(30) not null,
    `db_config_id` varchar(30) not null,
    `sql`          varchar(60) not null comment '执行的 sql 语法',
    `operation`    tinyint default 0 comment '默认 0，case 执行前 ， -1  case执行结束后执行 ',
    primary key (`id`)

) comment 'db_operation';










