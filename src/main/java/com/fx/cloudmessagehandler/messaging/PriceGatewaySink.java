package com.fx.cloudmessagehandler.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface PriceGatewaySink {

    String INPUT = "sink_price";

    @Input(PriceGatewaySink.INPUT)
    SubscribableChannel input();
}
