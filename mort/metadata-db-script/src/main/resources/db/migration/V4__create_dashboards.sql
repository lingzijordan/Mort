create table bigeye_dashboards
(
	id  				int primary key auto_increment  		comment '主键',
	name				varchar(50) 	not NULL         		comment '名称',
	description    		varchar(50)         	 default '无' 	comment '描述',
	created_at 			datetime    	not NULL	           	comment '创建日期',
	updated_at         	datetime                           		comment '修改日期',
	created_by          varchar(50) 	not NULL default 'admin' comment '创建人',
	updated_by          varchar(50) 	not NULL default '无' 	comment '修订者'
);


create table bigeye_join_dashboard_view
(
	dashboard_id  int  not NULL comment '引用dashboards主键'  REFERENCES bigeye_dashboards(id) ,
	view_id	      int  not NULL comment '引用views主键'       REFERENCES bigeye_views(id),
	view_order    int  not NULL comment 'view在dashboard中的顺序',
	primary key(dashboard_id,view_id)
);

create index ind_dash_board_id on bigeye_join_dashboard_view(dashboard_id);
create index ind_view_id       on bigeye_join_dashboard_view(view_id);