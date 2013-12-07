# Build file for the grails blog docker image
#
# Can be build by docker using the command:
#  docker build -t charliek/grails-blog .
#
# Can be run by docker using the command:
#  sudo docker run -p 8080:8080 -e grails.work.dir=/opt/apps/grails-blog -t charliek/grails-blog
#

FROM charliek/jetty9
MAINTAINER charlie.knudsen@gmail.com

RUN mkdir -p /opt/apps/grails-blog
ADD target/blog-0.1-SNAPSHOT.war /opt/apps/grails-blog/grails-blog.war

EXPOSE 8080
ENTRYPOINT $JAVA_HOME/bin/java -jar $JETTY_JAR /opt/apps/grails-blog/grails-blog.war
