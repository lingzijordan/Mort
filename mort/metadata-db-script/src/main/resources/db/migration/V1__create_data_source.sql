create table if not exists bigeye_data_sources
(
   id      int primary key auto_increment comment '主键',
   name    varchar(50) not null comment '数据源名称',
   description    text comment '数据源描述',
   data_source_type varchar(50)  default "rdb" not null comment '数据源类型 rdb:关系数据库 nosql:nosql file:文件系统',
   created_at datetime not null comment '创建时间',
   updated_at datetime not null comment '修改时间',
   created_by varchar(50) not null comment '创建者',
   updated_by varchar(50) not null comment '最后修改者'
);


create table if not exists bigeye_rdb_data_sources
(
  id    int primary key comment '主键, 使用data_sources的主键',
  host  varchar(50)   not null comment '关系数据库ip地址',
  port   int  not null comment '关系数据库端口',
  database_name  varchar(50)   not null comment '关系数据库名',
  user_name varchar(50) not null comment '关系数据库用户名',
  password varchar(50) not null comment '关系数据库密码',
  database_type varchar(50) not null comment '数据库类型 oracle mysql postgresql'
);