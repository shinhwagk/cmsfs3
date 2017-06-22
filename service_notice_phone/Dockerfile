FROM node:8

RUN mkdir /opt/cmsfs

WORKDIR /opt/cmsfs

ADD src .
ADD package.json .

RUN npm i

CMD node src/main.js