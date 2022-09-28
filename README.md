### Summit 2021 Integration Lab: Toll Station Events Aggregator

Camel K integration

Aggregates toll station events from the central Kafka cluster.

Stores license plates events as records in a PostgreSQL database.

#### Application properties example

```
camel.component.kafka.brokers=localhost:9092
camel.component.kafka.security-protocol=SASL_SSL
camel.component.kafka.sasl-mechanism=PLAIN
camel.component.kafka.sasl-jaas-config=org.apache.kafka.common.security.plain.PlainLoginModule required username="***" password="***" ;

# Kafka meter consumer properties 
consumer.topic=^.*-toll-station-events
consumer.topic.isPattern=true
consumer.group=toll-station-events
consumer.seekTo=beginning

quarkus.datasource.camel.jdbc.url=jdbc:postgresql://smartcity-db.user1-smartcity-central.svc:5432/smartcity 
quarkus.datasource.camel.username=smartcity_user
quarkus.datasource.camel.password=smartcity_pwd
```

#### Run the Integrations

```bash
oc create configmap toll-station-events --from-file=application.properties=toll-station-events.properties

kamel run --dev src/main/java/TollStationEvents.java \
  --build-property quarkus.datasource.camel.db-kind=postgresql \
  --config configmap:toll-station-events \
  -d mvn:io.quarkus:quarkus-jdbc-postgresql:2.0.0.Final \
  -n user1-smartcity-central
```