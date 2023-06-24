# for this example we chosen a light image using alpine
# but according to following website, it is better to use 
# debian based image (eg: node:20.2.0-bullseye-slim)
# @see https://snyk.io/blog/choosing-the-best-node-js-docker-image/
FROM node:20.2.0-alpine3.17

VOLUME /usr/src/app/react-dojo/node_modules

WORKDIR /usr/src/app/react-dojo

COPY package*.json ./

RUN npm ci

COPY . .

EXPOSE 3000

CMD ["npm", "start"]
