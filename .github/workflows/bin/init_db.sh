#!/bin/bash

# Set env
set -o allexport
PGHOST="$POSTGRES_HOST"
PGPORT="$POSTGRES_PORT"
PGDATABASE="$POSTGRES_DB"
PGUSER="$POSTGRES_USER"
PGPASSWORD="$POSTGRES_PASSWORD"
set +o allexport

# Run
psql -f docker/postgresql/sql/01-create-users-table.sql
psql -f docker/postgresql/sql/02-create-email-verification-tokens-table.sql
psql -f docker/postgresql/sql/03-create-reset-password-tokens-table.sql
psql -f docker/postgresql/sql/04-create-new-email-verification-tokens-table.sql
psql -f docker/postgresql/sql/05-create-plans-table.sql
psql -f docker/postgresql/sql/06-create-waypoint-lists-table.sql
#psql -f docker/postgresql/sql/07-create-start-places-table.sql
#psql -f docker/postgresql/sql/08-create-destinations-table.sql
#psql -f docker/postgresql/sql/09-create-route-infos-table.sql
#psql -f docker/postgresql/sql/10-create-belongings-table.sql
