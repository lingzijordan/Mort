#!/usr/bin/env bash

DEV_DATABASE="bigeye_dev"
TEST_DATABASE="bigeye_test"

. ${MORT_HOME}/scripts/create_database.sh

function init {
    create_mysql_db $1 bigeye bigeye123
    migrate_db $1
}

function run {
    prompt "Start mort server..."
    cd ${MORT_HOME}
    sbt domainService/compile:run
}
function gen_idea {
    prompt "Generate idea project..."
    cd ${MORT_HOME}
    sbt gen-idea
    prompt "Success idea project"
}

function init_dev {
    init ${DEV_DATABASE}
}

function sbt_test {
    prompt "Begin test project..."
    init_test
    cd ${MORT_HOME}
    sbt test -Denv=test
    RETVAL=$?

    if [ $RETVAL = 0 ]
    then
        ok "Done."
    else
        error "Test Failed. See error code for more information."
    fi
    return $RETVAL
}

function init_test {
    init ${TEST_DATABASE}
}

function migrate_dev_db {
    migrate_db ${DEV_DATABASE}
}

function migrate_db {
    prompt "Migrate database..."
    host=localhost
    if [ $# = 2 ]
    then
      host=$2
    fi
    cd ${MORT_HOME}/metadata-db-script
    sbt flywayMigrate -Dflyway.url=jdbc:mysql://$host/$1
    cd ${MORT_HOME}
    ok "Success migrate database"
}

function generate_all_model {
    generate_model bigeye_data_sources CustomerDataSource
    generate_model bigeye_rdb_data_sources DatabaseCustomerDataSource
    generate_model bigeye_data_sets DataSet
    generate_model bigeye_fields Field
    generate_model bigeye_reports Report
    generate_model bigeye_views View
    generate_model bigeye_join_view_field ViewField
    generate_model bigeye_dashboards Dashboard
    generate_model bigeye_join_dashboard_view DashboardView
}

function generate_model {
    EXPECTED_ARGS=2
    if [ $# -ne $EXPECTED_ARGS ]
    then
      error "Please use right parameters"
      error "Usage: ./go.sh gm tableName modelName"
      exit $E_BADARGS
    fi
    prompt "Generate models $1 ..."
    cd ${MORT_HOME}
    rm ${MORT_HOME}/models/src/main/scala/com/bigeyedata/mort/infrastructure/metadata/models/$2.scala
    sbt models/"scalikejdbcGen $1 $2"
    rm ${MORT_HOME}/models/src/test/scala/com/bigeyedata/mort/infrastructure/metadata/models/$2Spec.scala
    ok "Success generate $1 model from database"
}


function assembly {
    prompt "Assembly mort standslone jar..."
    cd ${MORT_HOME}
    rm ${MORT_HOME}/domain-service/target/scala-2.10/mort.jar
    sbt domainService/assembly
    cp ${MORT_HOME}/domain-service/target/scala-2.10/mort.jar ${MORT_HOME}/target/mort.jar

    RETVAL=$?

    if [ $RETVAL = 0 ]
    then
        ok "Done."
    else
        error "Package Failed. See error code for more information."
    fi
    return $RETVAL
}

function export_data {
    prompt "Generate data ..."
    cd ${MORT_HOME}/metadata-db-script

    temp=temp.sql

    exportTo=src/main/resources/db/migration/V100__fixture_data.sql
    mysqldump -ubigeye -pbigeye123 --skip-opt bigeye_dev > ${exportTo}
    sed 's/CREATE TABLE/CREATE TABLE IF NOT EXISTS/g' ${exportTo} > ${temp}
    sed '/INSERT INTO \`schema_version\`/d' ${temp} > ${exportTo}

    rm ${temp}
    ok "Success export data from database"
}

function deploy {
    EXPECTED_ARGS=2
    if [ $# -ne $EXPECTED_ARGS ]
    then
      error "Please use right parameters"
      error "Usage: ./go.sh deploy host username"
      exit $E_BADARGS
    fi
    ok 'Begin deploy mort to '$1
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
    ssh $2@$1 "/home/"$2"/mort/mort.sh create_db"
    migrate_db $DEV_DATABASE $1
}

function send_files {
    ssh $2@$1 "mkdir /home/"$2"/mort"
    scp ${MORT_HOME}/target/mort.jar $2@$1:/home/$2/mort/mort.jar
    scp ${MORT_HOME}/scripts/start.sh $2@$1:/home/$2/mort/start.sh
    scp ${MORT_HOME}/scripts/create_database.sh $2@$1:/home/$2/mort/create_database.sh
    scp ${MORT_HOME}/scripts/mort.sh $2@$1:/home/$2/mort/mort.sh
}

function start {
    echo -n "Starting mort..."

    ssh $2@$1 "/home/"$2"/mort/mort.sh start" $2

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
    echo -n "Stopping mort..."

    ssh $2@$1 "/home/"$2"/mort/mort.sh stop"
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