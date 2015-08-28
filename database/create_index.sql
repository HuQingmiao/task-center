drop index idx_app_call_log on app_call_log;
drop index idx_app_reg on app_reg;
drop index idx_app_reg_2 on app_reg;
drop index idx_schedule_control on schedule_control;

drop index idx_event_control on event_control;
drop index idx_operate_log on operate_log;
drop index idx_sys_codes on sys_codes;


create index idx_app_call_log on app_call_log (app_id,app_code);

create unique index idx_app_reg on app_reg (app_code);

create unique index idx_app_reg_2 on app_reg (app_name);

create unique index idx_schedule_control on schedule_control (app_id);

create unique index idx_event_control on event_control (app_id);

create index idx_operate_log on operate_log (op_table);

create index idx_sys_codes on sys_codes (kind,code);
