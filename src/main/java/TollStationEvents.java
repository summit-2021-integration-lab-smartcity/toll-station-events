import org.apache.camel.builder.RouteBuilder;

public class TollStationEvents extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        log.info("About to start route: Kafka Server -> Log");

        from("kafka:{{consumer.topic}}?"
                + "topicIsPattern={{consumer.topic.isPattern}}"
                + "&seekTo={{consumer.seekTo}}"
                + "&groupId={{consumer.group}}")
                .routeId("LicensePLatesFromKafka")
                .unmarshal().json()
                .log("Kafka message body: ${body}")
                .setBody(simple("INSERT INTO toll_station_events (toll_station, license_plate, lp_status, lp_timestamp) VALUES ('${body[station]}', '${body[licenseplate]}', '${body[status]}', '${body[timestamp]}') RETURNING id;"))
                .log("SQL statement: ${body}")
                .to("jdbc:camel");
    }
}
