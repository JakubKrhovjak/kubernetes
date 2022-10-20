FROM eclipse-temurin:17-jre-focal

ENV WORKING_DIR /opt/simple-rest

ARG JAR_FILE=target/simple-rest-*.jar

WORKDIR ${WORKING_DIR}

COPY ${JAR_FILE} ${WORKING_DIR}/simple-rest.jar

ENV JAVA_OPTS="-Xmx512M -XX:MaxMetaspaceSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$WORKING_DIR -Dfile.encoding=UTF-8"

ENTRYPOINT exec java $JAVA_OPTS -jar ${WORKING_DIR}/simple-rest.jar
