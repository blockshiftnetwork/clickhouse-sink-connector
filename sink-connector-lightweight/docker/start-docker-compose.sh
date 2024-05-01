#!/bin/bash


if [ -z $1 ]
then
  echo 'Using the latest tag for Sink connector'
  export CLICKHOUSE_SINK_CONNECTOR_LT_IMAGE='altinity/clickhouse-sink-connector:2.0.2-lt'
else
  export CLICKHOUSE_SINK_CONNECTOR_LT_IMAGE=$1
fi

./stop-docker-compose.sh
# Altinity sink images are tagged daily with this tag yyyy-mm-dd(2022-07-19)

docker compose -f docker-compose-mysql-standalone.yml up -d
