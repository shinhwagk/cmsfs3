FROM python:3

RUN apt update
RUN apt install libaio

ADD instantclient /usr/lib/instantclient

RUN cd /usr/lib/instantclient && ln -s libclntsh.so.12.1 libclntsh.so

ENV ORACLE_HOME /usr/lib/instantclient
ENV LD_LIBRARY_PATH $LD_LIBRARY_PATH:$ORACLE_HOME

RUN python -m pip install cx_Oracle