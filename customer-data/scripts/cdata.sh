#!/usr/bin/env bash

CDATA_HOME=$HOME"/cdata"

. $CDATA_HOME/create_database.sh

start () {
    echo -n "Starting cdata..."
    start-stop-daemon --start --oknodo --name cdata --user $1 --make-pidfile --pidfile ~/cdata.pid --startas $CDATA_HOME/start.sh --background -- --daemon

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
    echo -n "Stopping cdata..."

    start-stop-daemon --stop --pidfile ~/cdata.pid --retry 5
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
        create_mysql_db "customerdata_dev" "bigeye" "bigeye123"
    ;;
    *)
        echo $"Usage: cdata {start|stop}"
        exit 3
    ;;
esac
