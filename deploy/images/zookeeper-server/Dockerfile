FROM oracle/serverjre:8

ADD kafka_2.12-0.10.2.0.tgz .

WORKDIR kafka_2.12-0.10.2.0

ADD zookeeper.properties zookeeper.properties

CMD bin/zookeeper-server-start.sh config/zookeeper.properties