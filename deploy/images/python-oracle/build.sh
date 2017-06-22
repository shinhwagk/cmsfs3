#!/bin/bash
unzip -o lib/instantclient-basic-linux.x64-12.2.0.1.0.zip
unzip -o lib/instantclient-sdk-linux.x64-12.2.0.1.0.zip

mv instantclient_12_2 instantclient

docker build -t python-oracle:12.2 .