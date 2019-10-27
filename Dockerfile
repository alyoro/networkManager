FROM azul/zulu-openjdk-alpine:11
COPY /build/libs/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dcom.sun.jndi.ldap.object.disableEndpointIdentification=true -jar app.jar"]
