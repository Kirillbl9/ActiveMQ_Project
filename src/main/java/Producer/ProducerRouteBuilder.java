package Producer;

import org.apache.camel.builder.RouteBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class ProducerRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {
        from("timer:foo?period=3000")
                .setBody(() -> new Date())
                .to("jms:topic:newtopic");
    }
}