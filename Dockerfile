FROM amazoncorretto:17 as builder

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} application.jar

RUN java -Djarmode=layertools -jar application.jar extract

FROM amazoncorretto:17

ARG PORT=9004

COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./

EXPOSE ${PORT}

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]