FROM gradle:jdk

ENV WORK=/opt/helmes
WORKDIR ${WORK}
COPY . ${WORK}

EXPOSE 8080

RUN gradle clean build

CMD java -jar ${WORK}/backend/build/libs/backend-1.0-SNAPSHOT.jar