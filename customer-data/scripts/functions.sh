#!/usr/bin/env bash
DEV_DATABASE="customerdata_dev"
TEST_DATABASE="customerdata_test"

. ${CDATA_HOME}/scripts/create_database.sh

function init {
    create_mysql_db $1 bigeye bigeye123
    init_customer_data $1
}

function run {
    prompt "Start customer data server..."
    cd ${CDATA_HOME}
    sbt dbDomainService/compile:run
}

function init_dev {
    init ${DEV_DATABASE}
}

function gen_idea {
    prompt "Generate idea project..."
    cd ${CDATA_HOME}
    sbt gen-idea
    prompt "Success idea project"
}

function sbt_test {
    prompt "Generate idea project..."
    cd ${CDATA_HOME}
    sbt test
    RETVAL=$?

    if [ $RETVAL = 0 ]
    then
        ok "Done."
    else
        error "Test Failed. See error code for more information."
    fi
    return $RETVAL
}

function init_customer_data {
    prompt "Initialize database..."
    host=localhost
    if [ $# = 2 ]
    then
      host=$2
    fi
    cd ${CDATA_HOME}/customer-db-script
    sbt flywayMigrate -Dflyway.url=jdbc:mysql://${host}/$1
    cd ${CDATA_HOME}
    ok "Success init database"
}


function assembly {
    prompt "Assembly customer data standalone jar..."
    cd ${CDATA_HOME}
    rm ${CDATA_HOME}/db-domain-service/target/scala-2.10/customerData.jar
    sbt dbDomainService/assembly
    cp ${CDATA_HOME}/db-domain-service/target/scala-2.10/customerData.jar ${CDATA_HOME}/target/customerData.jar

    RETVAL=$?

    if [ $RETVAL = 0 ]
    then
        ok "Done."
    else
        error "Package Failed. See error code for more information."
    fi
    return $RETVAL
}

function deploy {
    EXPECTED_ARGS=2
    if [ $# -ne $EXPECTED_ARGS ]
    then
      error "Please use right parameters"
      error "Usage: ./go.sh deploy host username"
      exit $E_BADARGS
    fi
    ok 'Begin deploy cdata to '$1
    stop $1 $2
    send_files $1 $2
    prepare_db $1 $2
    RETVAL=$?
    if [ $RETVAL -ne 0 ]
    then
        return $RETVAL
    fi
    start $1 $2
    RETVAL=$?
    if [ $RETVAL -ne 0 ]
    then
        return $RETVAL
    fi
}

function prepare_db {
    ssh $2@$1 "/home/"$2"/cdata/cdata.sh create_db"
    init_customer_data ${DEV_DATABASE} $1
}

function send_files {
    ssh $2@$1 "mkdir /home/"$2"/cdata"
    scp ${CDATA_HOME}/target/customerData.jar $2@$1:/home/$2/cdata/customerData.jar
    scp ${CDATA_HOME}/scripts/start.sh $2@$1:/home/$2/cdata/start.sh
    scp ${CDATA_HOME}/scripts/create_database.sh $2@$1:/home/$2/cdata/create_database.sh
    scp ${CDATA_HOME}/scripts/cdata.sh $2@$1:/home/$2/cdata/cdata.sh
}

function start {
    echo -n "Starting cdata..."

    ssh $2@$1 "/home/"$2"/cdata/cdata.sh start" $2

    RETVAL=$?
    if [ $RETVAL = 0 ]
    then
        echo "done."
    else
        echo "failed. See error code for more information."
    fi
    return $RETVAL
}

function stop {
    echo -n "Stopping cdata..."

    ssh $2@$1 "/home/"$2"/cdata/cdata.sh stop"
    RETVAL=$?

    if [ $RETVAL = 0 ]
    then
        echo "Done."
    else
        echo "Failed. See error code for more information."
    fi
    return $RETVAL
}

function ok {
    echo -e '\033[0;32m'$1'\033[0m';
}

function error {
    echo -e '\033[0;31m'$1'\033[0m';
}

function prompt {
    echo -e '\033[0;34m'$1'\033[0m';
}

