#!/usr/bin/env bash

CDATA_HOME=$(pwd)

. ${CDATA_HOME}/scripts/functions.sh

prompt " __  __ _________ _____ ";
prompt "|  \/  / () | () |_   _|";
prompt "|_|\/|_\____|_|\_\ |_|  ";

function show_help {
	ok "Usage: go.sh [COMMAND]"
	ok "COMMAND"
	ok "======================================================================================================"
	ok "init:  \t\tInitlize development environment. This will do the following things:\n \t\t\t1. Create customer database\n \t\t\t2. prepare customer data.\n"
	ok "idea: \t\tGenerate IntellJ IDEA project"
	ok "run: \t\tStart mort server"
	ok "test: \t\tRun test on bigeye_test database"
	ok "package: \t\tPackage all the class and dependencies jars into a standalone jar"
	ok "======================================================================================================"
}

function main {
	case $1 in
		init) init_dev;;
		idea) gen_idea;;
		run) run;;
		test) sbt_test;;
		package) assembly;;
		deploy) deploy $2 $3;;
		*) show_help && exit 1;;
	esac
}

main $@
