FROM maven:alpine AS maven_c
WORKDIR /app
COPY . .
RUN  mvn clean package
RUN ls -l
FROM openjdk:8
RUN pwd
RUN ls
COPY --from=maven_c  /app/target/blog_ecs.jar . 
EXPOSE 9002
RUN ls -l
CMD ["java","-jar","blog_ecs.jar"]

