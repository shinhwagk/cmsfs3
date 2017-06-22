FROM node:8

RUN mkdir /opt/cmsfs

WORKDIR /opt/cmsfs

RUN npm i typescript -g
RUN npm i @types/koa@"^2.0.39",
RUN npm i @types/koa-router@"^7.0.22"
RUN npm i koa@"^2.2.0"
RUN npm i koa-router@"^7.2.1
RUN npm i mysql2@"^1.3.5"
RUN npm i @types/node@"^7.0.31"

ADD tsconfig.json .
ADD index.js .

RUN tsc .

ADD src .

CMD node index.js