/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015-5-22 10:57:40                           */
/*==============================================================*/


drop table if exists app_call_log;

drop table if exists app_reg;

drop table if exists event_control;

drop table if exists operate_log;

drop table if exists schedule_control;

-- drop table if exists sys_codes;

/*==============================================================*/
/* Table: app_call_log                                          */
/*==============================================================*/
create table app_call_log
(
   id                   bigint not null auto_increment,
   app_id               bigint not null,
   app_code             varchar(30) not null,
   action               varchar(10) not null,
   time                 datetime not null,
   result               int,
   exception            varchar(80),
   primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='应用调度日志表';

/*==============================================================*/
/* Table: app_reg                                               */
/*==============================================================*/
create table app_reg
(
   id                   bigint not null auto_increment,
   app_name             varchar(90) not null,
   app_code             varchar(30),
   host_name            varchar(20) not null,
   type                 int,
   command              varchar(100) not null,
   state                int,
   create_user          varchar(20),
   create_time          datetime not null,
   primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='应用登记表';

/*==============================================================*/
/* Table: event_control                                         */
/*==============================================================*/
create table event_control
(
   id                   bigint not null auto_increment,
   app_id               bigint not null,
   enable               int not null,
   primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='MQ调度控制表';

/*==============================================================*/
/* Table: operate_log                                           */
/*==============================================================*/
create table operate_log
(
   id                   bigint not null auto_increment,
   op_table             varchar(30) not null,
   op_data_desc         varchar(80) not null,
   update_content       varchar(220),
   op_user              varchar(30),
   op_time              date not null,
   primary key (id)
);

/*==============================================================*/
/* Table: schedule_control                                      */
/*==============================================================*/
create table schedule_control
(
   id                   bigint not null auto_increment,
   app_id               bigint not null,
   cron_expression      varchar(50) not null,
   next_call_time       datetime,
   enable               int not null,
   primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='时间调度控制表';

/*==============================================================*/
/* Table: sys_codes                                             */
/*==============================================================*/
-- create table sys_codes
-- (
--    id                   bigint not null auto_increment,
--    kind                 varchar(30) not null,
--    kind_desc            varchar(40) not null,
--    code                 varchar(30) not null,
--    code_desc            varchar(50) not null,
--    order_by             int not null,
--    primary key (id)
-- );

