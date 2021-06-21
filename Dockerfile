FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8080
ADD target/geotechmap.jar geotechmap.jar
ENTRYPOINT ["java","-jar","/geotechmap.jar"]

