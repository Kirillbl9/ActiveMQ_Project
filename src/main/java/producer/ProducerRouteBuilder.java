package producer;

import model.User;
import org.apache.camel.builder.RouteBuilder;

public class ProducerRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
      //User user = new User(1, "Tom");
        from("direct:start").split().body()
               // .setBody(user::toJson)
                .to("jms:topic:newtopic");
    }
}