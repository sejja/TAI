FROM node:14.18-alpine AS build
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine AS prod-stage
COPY --from=build /app/dist/DBCS-Cliente-alferna-josborg /usr/share/nginx/html
COPY ./nginx-custom.conf /etc/nginx/conf.d/default.conf
ENV PORT 4200
EXPOSE $PORT
CMD ["nginx", "-g", "daemon off;"]
