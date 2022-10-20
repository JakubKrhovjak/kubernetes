FROM eclipse-temurin:17-jre-focal

ENV WORKING_DIR /opt/web-flux

ARG JAR_FILE=target/web-flux-*.jar

WORKDIR ${WORKING_DIR}

COPY ${JAR_FILE} ${WORKING_DIR}/web-flux.jar

ENV JAVA_OPTS="-Xmx512M -XX:MaxMetaspaceSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$WORKING_DIR -Dfile.encoding=UTF-8"

ENTRYPOINT exec java $JAVA_OPTS -jar ${WORKING_DIR}/web-flux.jar
