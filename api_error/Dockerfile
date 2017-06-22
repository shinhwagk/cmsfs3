FROM node:8

RUN mkdir /opt/cmsfs

WORKDIR /opt/cmsfs

ADD src .
ADD gulpfile.js .
ADD package.json .
ADD tsconfig.json .
ADD index.js .

RUN npm i && npm run gulp

CMD node index.js