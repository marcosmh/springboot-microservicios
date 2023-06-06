#!/bin/sh
export RABBIT_ADDRESSES=localhost:5672
export STORAGE_TYPE=mysql
export MYSQL_USER=zipkin
export MYSQL_PASS=zipkin
export MYSQL_USE_SSL=false

java -jar zipkin-server-2.24.1-exec.jar
