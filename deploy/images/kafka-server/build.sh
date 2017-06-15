#!/bin/bash

SCRIPT_DIR=`dirname $0`;
BUILD_DIR="/tmp/$(date +%s%N)";

mkidr $BUILD_DIR && cd $BUILD_DIR;
wget http://apache.fayea.com/kafka/0.10.2.0/kafka_2.12-0.10.2.0.tgz

docker build -t cmsfs3/kafka-server .