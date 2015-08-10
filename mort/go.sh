#!/usr/bin/env bash

MORT_HOME=$(pwd)

. ${MORT_HOME}/scripts/functions.sh

prompt " __  __ _________ _____ ";
prompt "|  \/  / () | () |_   _|";
prompt "|_|\/|_\____|_|\_\ |_|  ";

function show_help {
	ok "Usage: go.sh [COMMAND]"
	ok "COMMAND"
	ok "======================================================================================================"
	ok "init: \t\tInitlize development environment. This will do the following things:\n \t\t\t1. Create database with bigeye name\n \t\t\t2. Migrage database\n"
	ok "migrate: \tExecute database migration on development database"
	ok "gm: \t\tGenerate scalikejdbc model from database for specific table"
	ok "gm_all: \tGenerate scalikejdbc model from database for all tables"
	ok "idea: \t\tGenerate IntellJ IDEA project"
	ok "run: \t\tStart mort server"
	ok "test: \t\tRun test on bigeye_test database"
	ok "package: \t\tPackage all the class and dependencies jars into a standalone jar"
	ok "ed: \t\tExport Data based on your current database"
	ok "======================================================================================================"
}

function main {
	case $1 in
		init) init_dev;;
		migrate) migrate_dev_db;;
		gm) generate_model $2 $3;;
		gm_all) generate_all_model;;
		idea) gen_idea;;
		run) run;;
		test) sbt_test;;
		ed) export_data;;
		package) assembly;;
		deploy) deploy $2 $3;;
		*) show_help && exit 1;;
	esac
}

main $@
