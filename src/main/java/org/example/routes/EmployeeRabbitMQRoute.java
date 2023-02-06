package org.example.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.example.model.Employee;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class EmployeeRabbitMQRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        JacksonDataFormat jsonFormat = new JacksonDataFormat(Employee.class);
        log.info("Called EmployeeRabbitMQRoute router....");
        /*from("direct:start")
                .id("direct-fetchEmployee")
                .marshal(jsonFormat)
                .to("rabbitmq://localhost:5672/employee.exchange?queue=employee.queue&autoDelete=false")
                .end();*/
        /*any changes to make to the message using various process same updated message will be passed on same route.
           like enrich or tweak messages.
         */
        from("rabbitmq:employee.direct?queue=employee.queue&routingKey=employee&autoDelete=false")
                .log(LoggingLevel.ERROR, "Before Enrichment : ${body}")
                .unmarshal().json(JsonLibrary.Jackson, Employee.class)
                .process(this::enrichEmployee)
                .log(LoggingLevel.ERROR, "After Enrichment : ${body}")
                .marshal().json(JsonLibrary.Jackson, Employee.class)//print the content in the exchange body
        .to("rabbitmq:employee.direct?queue=employee-event.queue&routingKey=employee-event&autoDelete=false")//write to another queue
        .to("file:///Users/pradeep/tmp/camel-demos/?fileName=employee-event.txt&fileExist=Append");//write to file and append to file if file exists
    }

    private void enrichEmployee(Exchange exchange) {
        Employee employee= exchange.getMessage().getBody(Employee.class);
        employee.setReceivedTime(new Date().toString());

        //update exchange body message
        Message updatedMessage = new DefaultMessage();
        updatedMessage.setBody(employee);
        exchange.setMessage(updatedMessage);

    }
}
