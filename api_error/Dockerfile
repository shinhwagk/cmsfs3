FROM node:8

RUN mkdir /opt/cmsfs

WORKDIR /opt/cmsfs

RUN npm i typescript -g
RUN npm i @types/node@"^7.0.31"

RUN npm i koa@"^2.2.0"
RUN npm i koa-router@"^7.2.1
RUN npm i koa-bodyparser@"^4.2.0"
RUN npm i kafka-node@"^1.6.2"

RUN npm i @types/koa@"^2.0.39"
RUN npm i @types/koa-router@"^7.0.22"
RUN npm i @types/koa-bodyparser@"^3.0.23"
RUN npm i @types/kafka-node@"^1.3.4"

ADD tsconfig.json .
ADD index.js .

ADD src .

RUN tsc .

CMD node index.js