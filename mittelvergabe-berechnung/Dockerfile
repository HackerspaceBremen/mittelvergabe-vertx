FROM hypriot/rpi-java:latest

ENV VERTICLE_FILE mittelvergabe-calc-java-1.0-SNAPSHOT-fat.jar
ENV VERTICLE_HOME /usr/verticles

COPY target/$VERTICLE_FILE $VERTICLE_HOME/

WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar $VERTICLE_FILE -cluster"]