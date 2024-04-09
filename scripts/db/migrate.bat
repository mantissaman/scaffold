@echo off
SET SCRIPT_HOME=%~dp0
echo %SCRIPT_HOME%

set DB_HOST=localhost
set DB_PORT=5432
set DB_USER=archuser
set DB_NAME=arch
set DB_PWD=pa55word

cd %SCRIPT_HOME%

flyway -url="jdbc:postgresql://%DB_HOST%:%DB_PORT%/%DB_NAME%?user=%DB_USER%&password=%DB_PWD%" -locations="filesystem:/%SCRIPT_HOME%\migrations" -outOfOrder=true migrate -connectRetries=50