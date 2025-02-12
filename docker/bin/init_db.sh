#!/bin/bash

# Get all .sql files
SQL_DIRECTORY=$1
SQL_LIST=$(find "$SQL_DIRECTORY" -type f -name "[0-9][0-9]-*.sql" | sort -V)

# Set env
set -o allexport
PGHOST="$POSTGRES_HOST"
PGPORT="$POSTGRES_PORT"
PGDATABASE="$POSTGRES_DB"
PGUSER="$POSTGRES_USER"
PGPASSWORD="$POSTGRES_PASSWORD"
set +o allexport

# Run
for SQL in $SQL_LIST; do
  echo "> run \"psql -f \"$SQL\"\""
  psql -f "$SQL"
done
