package com.fx.cloudmessagehandler;

import com.fx.cloudmessagehandler.messaging.PriceGatewaySink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding(PriceGatewaySink.class)
public class CloudMessageHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudMessageHandlerApplication.class, args);
    }
}
