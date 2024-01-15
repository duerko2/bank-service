FROM adoptopenjdk:11-jre-hotspot
COPY target/lib /usr/src/lib
COPY target/bank-service-1.0.0.jar /usr/src/
WORKDIR /usr/src/
CMD java -Xmx32m -jar bank-service-1.0.0.jar
