@echo off
SET SCRIPT_HOME=%~dp0
echo %SCRIPT_HOME%

set POSTGRES_USER=postgres
set POSTGRES_PASSWORD=pa55word
set POSTGRES_DB=postgres

set DB_HOST=localhost
set DB_PORT=5432
set DB_USER=archuser
set DB_NAME=arch
set DB_PWD=pa55word

echo Recreating local db started

set PGPASSWORD=%POSTGRES_PASSWORD%

(
echo DROP DATABASE IF EXISTS %DB_NAME%;
echo DROP USER IF EXISTS %DB_USER%;
) | psql -h %DB_HOST% -p %DB_PORT% -U "%POSTGRES_USER%" -d "%POSTGRES_DB%"

(
echo CREATE USER %DB_USER% with password '%DB_PWD%';
echo CREATE DATABASE %DB_NAME%;
echo GRANT ALL PRIVILEGES ON DATABASE %DB_NAME% TO %DB_USER%;
echo ALTER USER %DB_USER% WITH SUPERUSER;
) | psql -h %DB_HOST% -p %DB_PORT% -U "%POSTGRES_USER%" -d "%POSTGRES_DB%"

