
FROM node:latest AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod
FROM nginx:latest
RUN rm -rf /usr/share/nginx/html/*

COPY --from=builder /app/dist/smart-rh-angular /usr/share/nginx/html
EXPOSE 80