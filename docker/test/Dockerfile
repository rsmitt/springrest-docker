FROM ubuntu
MAINTAINER Petr Petrov "petrov@gmail.com"
RUN apt-get -y update && apt-get install -y nginx
COPY default /etc/nginx/sites-available/default
COPY ./index.html /usr/share/nginx/html/index.html
CMD ["/usr/sbin/nginx", "-g", "daemon off;"]
EXPOSE 80