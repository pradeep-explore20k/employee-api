package org.example.config;

import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CamelConfiguration {
    //private final CachingConnectionFactory factory;
    public static final String RABBIT_URI = "rabbitmq:employee.direct?queue=%s&routingKey=%s&autoDelete=false";
    /*
        if the method name is "rabbitConnectionFactory" the spring container,
        the spring container automatically injected the bean, if not we need
        to pass in the "RouterBuilder" from() like
        "queue=employee.queue&autoDelete=false&connectionFcatory=connectionfactory"
     */
    @Bean
    public ConnectionFactory rabbitConnectionFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
        //return factory.getRabbitConnectionFactory();
    }
}
