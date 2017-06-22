FROM python:3

ADD instantclient /usr/lib/instantclient

RUN cd /usr/lib/instantclient && ln -s libclntsh.so.12.1 libclntsh.so

ENV ORACLE_HOME /opt/instantclient
ENV LD_LIBRARY_PATH $LD_LIBRARY_PATH:$ORACLE_HOME

RUN python -m pip install cx_Oracle