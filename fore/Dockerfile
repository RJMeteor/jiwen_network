FROM node:18.20.3
WORKDIR /app
COPY . .
RUN pwd
RUN 
RUN ls
RUN npm config set registry https://registry.npmmirror.com
RUN npm install
RUN npm run build