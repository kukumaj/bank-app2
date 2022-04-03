FROM tomcat:8.5.77-jre17-temurin

COPY build/libs/* /usr/local/tomcat/webapps/