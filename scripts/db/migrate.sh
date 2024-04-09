#!/bin/bash

SCRIPT_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

while true; do
    read -p "Please enter host environment[1-localhost]: " envtype
    case $envtype in
		1 | localhost ) DB_HOST=localhost;DB_NAME=arch;DB_PORT=5432;DB_USER=archuser;DB_PWD='pa55word'; break;;
        * ) echo "Invalid host environment!";;
    esac
done

echo "Rebuilding local DB started..."
base="$(date +%s)"

cd $SCRIPT_HOME
flyway -url="jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}?user=${DB_USER}&password=${DB_PWD}" -locations="filesystem:/${SCRIPT_HOME}/migrations" -outOfOrder=true migrate -connectRetries=50

if [[ $envtype =  1 ]]
then
    echo "Loading test data: "
    export PGPASSWORD=$DB_PWD
    psql  -h $DB_HOST -p $DB_PORT --username "$DB_USER" -d $DB_NAME -f $SCRIPT_HOME/test_data.sql
fi

after="$(date +%s)"
elasped_seconds="$(expr $after - $base)"
echo "Full Database build successfully completed in $elasped_seconds seconds."
