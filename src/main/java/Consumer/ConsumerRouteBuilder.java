package Consumer;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class ConsumerRouteBuilder extends RouteBuilder {
    @Override
    public void configure() {
        from("jms:topic:newtopic").process(new ConsumerProcessor());
    }

    static class ConsumerProcessor implements Processor {

        @Override
        public void process(Exchange exchange) {
            //обработка принятых данных
            System.out.println("Новое сообщение: " + exchange.getIn().getBody() + " от: " + exchange.getFromEndpoint().getEndpointUri());
        }
    }
}