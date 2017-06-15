FROM oracle/serverjre:8

ADD kafka_2.12-0.10.2.0.tgz .

WORKDIR kafka_2.12-0.10.2.0

ADD server.properties config/server.properties

CMD bin/kafka-server-start.sh config/server.properties