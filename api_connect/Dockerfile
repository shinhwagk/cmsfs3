FROM node:8

RUN mkdir /opt/cmsfs

WORKDIR /opt/cmsfs

ADD package.json .

RUN npm i koa@"^2.2.0"
RUN npm i koa-router@"^7.2.1"
RUN npm i koa-bodyparser@"^4.2.0"
RUN npm i kafka-node@"^1.6.2"



ADD src src

RUN tsc

CMD node index.js