@echo off
set SCRIPT_HOME=%~dp0
set APP_URL=http://localhost:3000

set DB_HOST=localhost
set DB_PORT=5432
set DB_NAME=arch
set DB_USERNAME=archuser
set DB_PASSWORD=pa55word

set EMAIL_DEBUG_NAME=BNP.DEV

cd %SCRIPT_HOME%
mvnw.cmd spring-boot:run

