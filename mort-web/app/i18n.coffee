dict =
	dataSources:
		"zh-CN":"数据源列表"
	dataSource:
		"zh-CN":"数据源"

#	===================DataSource Editor======================
	dataSourceType:
		"zh-CN":"数据源类型"

	database:
		"zh-CN":"数据库"
	databaseType:
		"zh-CN":"数据库类型"
	dataSourceName:
		"zh-CN":"数据源名称"
	description:
		"zh-CN":"描述"
	host:
		"zh-CN":"主机"
	port:
		"zh-CN":"端口"

	username:
		"zh-CN":"用户名"
	password:
		"zh-CN":"密码"

#	===================DataSource Editor======================
	datasets:
		"zh-CN":"数据集列表"
	dataset:
		"zh-CN":"数据集"

	add:
		"zh-CN":"添加"
	" ":
		"zh-CN":""
	submit:
		"zh-CN":"提交"

	check:
		"zh-CN":"检测"

#	===================Create Report======================
	reportName:
		"zh-CN":"报表名"
#	===================Create Report======================
	dashboardName:
		"zh-CN":"仪表盘名"
	views:
		"zh-CN":"请选择视图"

{language} = navigator
language = "zh-CN"

module.exports = (name)->
	dict[name]?[language] or name