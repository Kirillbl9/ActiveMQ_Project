import javax.jms.ConnectionFactory;

import consumer.ConsumerRouteBuilder;
import org.apache.camel.ProducerTemplate;
import producer.ProducerRouteBuilder;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        try {
            context.addRoutes(new ConsumerRouteBuilder());
            context.addRoutes(new ProducerRouteBuilder());
            context.start();
            ProducerTemplate template = context.createProducerTemplate();
            template.sendBody("direct:start", "hello");

            //имитация сообщений из других сервисов
            ExecutorService es = Executors.newFixedThreadPool(10);
            for (int i = 0; i < 1000; i++) {
                int finalI = i;
                es.submit(() -> {
                    int sleepTime = (int) (1 + Math.random() * 5);
                    sleepTime = sleepTime * 2000;
                    try {
                        sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    template.sendBody("direct:start",
                            "[hello by thread " + Thread.currentThread().getName() + " with task number: " + finalI + "]");
                });
            }
            sleep(100000);
        } finally {
            context.stop();
        }
    }
}