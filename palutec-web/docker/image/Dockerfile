### Run ###
FROM nginx:1.25.1-alpine-slim

ENV TZ="America/Sao_Paulo"

COPY ./dist/palutec-web/ /usr/share/nginx/html
COPY ./docker/image/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
