package consumer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.User;
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
            Gson gson = new Gson();
            User user = gson.fromJson(exchange.getMessage().getBody().toString(), User.class);
            System.out.println("Новое сообщение: " + user + " от: " + exchange.getFromEndpoint().getEndpointUri());
        }
    }
}