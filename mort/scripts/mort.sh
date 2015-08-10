#!/usr/bin/env bash

MORT_HOME=$HOME"/mort"

. $MORT_HOME/create_database.sh

start () {
    echo -n "Starting mort..."
    start-stop-daemon --start --oknodo --name mort --user $1 --make-pidfile --pidfile ~/mort.pid --startas $MORT_HOME/start.sh --background -- --daemon

    RETVAL=$?
    if [ $RETVAL = 0 ]
    then
        echo "done."
    else
        echo "failed. See error code for more information."
    fi
    return $RETVAL
}

stop () {
    echo -n "Stopping mort..."

    start-stop-daemon --stop --pidfile ~/mort.pid --retry 5
    RETVAL=$?

    if [ $RETVAL = 0 ]
    then
        echo "Done."
    else
        echo "Failed. See error code for more information."
    fi
    return $RETVAL
}


case "$1" in
    start)
        start $2
    ;;
    stop)
        stop
    ;;
    create_db)
        create_mysql_db "bigeye_dev" "bigeye" "bigeye123"
    ;;
    *)
        echo $"Usage: mort {start|stop}"
        exit 3
    ;;
esac
