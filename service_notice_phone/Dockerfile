FROM node:8

RUN mkdir /opt/cmsfs

WORKDIR /opt/cmsfs

RUN npm i kafka-node@"^1.6.2"
RUN npm i request@"^2.81.0"

ADD src src

CMD node src/main.js