FROM python:3

RUN mkdir /opt/cmsfs
WORKDIR /opt/cmsfs

ADD api_es.py .
ADD main.py .
ADD mdb.py .
ADD mhttp.py .

CMD python main.py