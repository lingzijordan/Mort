#!/usr/bin/env bash

function show_help {
	ok "Usage: mort.sh [COMMAND]"
	ok "COMMAND"
	ok "======================================================================================================"
	ok "init: \t\tInitlize development environment. This will do the following things:\n \t\t\t1. Install nginx\n \t\t\t2. Start Nginx\n"
	ok "start: \tStart nginx server, then you can access mort from http://localhost"
	ok "======================================================================================================"
}


function init {
	ok "Prepare install nginx"
	brew install nginx
	sudo cp nginx.conf /usr/local/etc/nginx/
	sudo nginx
}

function start {
	ok "Start nginx"
	sudo nginx	
}

function ok {
    echo -e '\033[0;32m'$1'\033[0m';
}

function main {
	case $1 in
		init) init;;
		start) start;;
		*) show_help && exit 1;;
	esac
}

main $@