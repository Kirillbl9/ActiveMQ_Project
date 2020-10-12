import javax.jms.ConnectionFactory;

import consumer.ConsumerRouteBuilder;
import producer.ProducerRouteBuilder;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        context.addRoutes(new ConsumerRouteBuilder());
        context.addRoutes(new ProducerRouteBuilder());
        context.start();
        Thread.sleep(100000);
        context.stop();

    }
}