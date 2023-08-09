FROM openjdk:17-jdk

EXPOSE 8099

ADD target/employeeapi.jar employeeapi.jar

ENTRYPOINT ["java", "-jar", "employeeapi.jar"]