#!/usr/bin/env bash

function create_mysql_db {
    echo "Create database..."
    EXPECTED_ARGS=3
    E_BADARGS=65
    MYSQL=`which mysql`

    Q0="DROP DATABASE IF EXISTS $1;"
    Q1="CREATE DATABASE $1 DEFAULT CHARACTER SET utf8;"
    Q3="GRANT ALL ON *.* TO '$2'@'%' IDENTIFIED BY '$3';"
    Q4="FLUSH PRIVILEGES;"
    SQL="${Q0}${Q1}${Q3}${Q4}"

    if [ $# -ne $EXPECTED_ARGS ]
    then
      echo "Usage: $0 dbname dbuser dbpassword"
      exit $E_BADARGS
    fi

    $MYSQL -uroot -e "$SQL"

    echo "Database $1 and user $2 created with a password $3"
}
