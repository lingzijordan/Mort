create table if not exists bigeye_data_sets
(
  id   int primary key auto_increment comment '主键',
  name varchar(50) not null comment '数据集名称',
  description text not null comment '数据集描述',
  status int not null comment '数据集导入状态,1:准备 2:正在导入 3:失败 4:完成',
  query_statement varchar(4000) not null comment '产生数据集的sql语句',
  execution_plan varchar(4000) not null comment '执行计划',
  table_name varchar(50) not null comment '数据集表名',
  created_at datetime not null comment '创建时间',
  updated_at datetime not null comment '修改时间',
  created_by varchar(50) not null comment '创建者',
  updated_by varchar(50) not null comment '最后修改者',
  data_source_id  int not null comment '外键,数据源id' references bigeye_data_sources(id)
);

create table if not exists bigeye_fields
(
 id int primary key not null auto_increment comment '主键',
 code_name varchar(50) not null comment '字段名称',
 alias_name varchar(50) not null comment '字段别名',
 field_name varchar(50) not null comment '原始字段名',
 field_type varchar(50) not null comment '字段类型',
 data_class_type varchar(200) not null comment '字段数据的Java类型',
 field_length int not null comment '字段类型的长度',
 scale int not null comment '字段类型针对double或float的小数位精度',
 data_set_id int not null comment '外键' references bigeye_data_sets(id)
);