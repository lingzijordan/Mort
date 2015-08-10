create table if not exists bigeye_reports
(
    id   int primary key auto_increment comment '主键',
    name varchar(50) not null comment '报表名称',
    description text not null comment '报表描述',
    created_at datetime not null comment '创建时间',
    updated_at datetime not null comment '修改时间',
    created_by varchar(50) not null comment '创建者',
    updated_by varchar(50) not null comment '最后修改者',
    data_set_id  int not null comment '外键,数据集id' references bigeye_data_sets(id)
);

create table if not exists bigeye_views
(
    id int primary key not null auto_increment comment '主键',
    name varchar(50) not null comment '视图名称',
    description text not null comment '视图描述',
    view_type varchar(50) not null comment '视图类型',
    generated_query text not null comment '执行语句',
    created_at datetime not null comment '创建时间',
    updated_at datetime not null comment '修改时间',
    created_by varchar(50) not null comment '创建者',
    updated_by varchar(50) not null comment '最后修改者',
    report_id  int not null comment '外键,报表id' references bigeye_reports(id)
);

create table if not exists bigeye_join_view_field
(
    id int primary key not null auto_increment comment '主键',
    view_id  int not null comment '外键,视图id' references bigeye_views(id),
    field_id  int not null comment '外键,字段id' references bigeye_fields(id),
    field_order int not null comment '字段顺序',
    field_type varchar(50) not null comment '针对当前视图下字段的类型',
    operation varchar(50) comment '操作名称',
    unique(view_id, field_order, field_type)
);