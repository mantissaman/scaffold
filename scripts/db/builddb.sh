#!/bin/bash

SCRIPT_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"


if [[ -z "$POSTGRES_USER" ]]; then
    export POSTGRES_USER=postgres
fi
if [[ -z "$POSTGRES_PASSWORD" ]]; then
    export POSTGRES_PASSWORD='pa55word'
fi
if [[ -z "$POSTGRES_DB" ]]; then
    export POSTGRES_DB=postgres
fi

export DB_HOST=localhost
export DB_PORT=5432
export DB_USER=archuser
export DB_NAME=arch
export DB_PWD='pa55word'


echo "Recreating local DB started..."
base="$(date +%s)"

export PGPASSWORD=$POSTGRES_PASSWORD
psql -h $DB_HOST -p $DB_PORT --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" << EOSQL
	UPDATE pg_database SET datallowconn = 'false' WHERE datname = '$DB_NAME';
	SELECT pg_terminate_backend(pid)
	FROM pg_stat_activity
	WHERE datname = '$DB_NAME';
	DROP DATABASE IF EXISTS $DB_NAME;
	DROP USER IF EXISTS $DB_USER;
EOSQL

psql -h $DB_HOST -p $DB_PORT --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" << EOSQL
	CREATE USER $DB_USER with password '$DB_PWD';
    CREATE DATABASE $DB_NAME;
    GRANT ALL PRIVILEGES ON DATABASE $DB_NAME TO $DB_USER;
	ALTER USER $DB_USER WITH SUPERUSER;

EOSQL

after="$(date +%s)"
elasped_seconds="$(expr $after - $base)"
echo "Database created successfully in $elasped_seconds seconds."
