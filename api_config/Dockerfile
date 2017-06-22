FROM node:8

RUN mkdir /opt/cmsfs

WORKDIR /opt/cmsfs

ADD gulpfile.js .
ADD package.json .
ADD tsconfig.json .
ADD index.js .

RUN npm i && npm run gulp

ADD src .

CMD node index.js