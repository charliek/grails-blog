# Build file for the grails blog docker image
#
# Can be build by docker using the command:
#  docker build -t charliek/grails-blog .
#
# Can be run by docker using the command:
#  sudo docker run -p 8080:8080 -e grails.work.dir=/opt/apps/grails-blog -t charliek/grails-blog
#

FROM ubuntu:precise
MAINTAINER charlie.knudsen@gmail.com

RUN echo "deb http://archive.ubuntu.com/ubuntu precise main universe" > /etc/apt/sources.list
RUN apt-get update && apt-get -y upgrade
RUN apt-get -y install openjdk-7-jre-headless curl
RUN echo "JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64/" >> /etc/environment

RUN mkdir -p /opt/apps/grails-blog
RUN curl http://repo1.maven.org/maven2/org/eclipse/jetty/jetty-runner/9.1.0.v20131115/jetty-runner-9.1.0.v20131115.jar -o /opt/apps/grails-blog/jetty-runner.jar
ADD target/blog-0.1-SNAPSHOT.war /opt/apps/grails-blog/grails-blog.war

EXPOSE 8080
ENTRYPOINT java -jar /opt/apps/grails-blog/jetty-runner.jar /opt/apps/grails-blog/grails-blog.war
 
