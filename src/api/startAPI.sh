#!/usr/bin/env bash
export APP_URL=http://localhost:3000

export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=arch
export DB_USERNAME=archuser
export DB_PASSWORD=pa55word

export EMAIL_DEBUG_NAME=BNP.DEV

./mvnw clean
./mvnw spring-boot:run