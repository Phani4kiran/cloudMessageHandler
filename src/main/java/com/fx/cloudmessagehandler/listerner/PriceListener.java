package com.fx.cloudmessagehandler.listerner;

import com.fx.cloudmessagehandler.messaging.PriceGatewaySink;
import com.fx.cloudmessagehandler.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;

@MessageEndpoint
public class PriceListener {

    private static final Logger logger = LoggerFactory.getLogger(PriceListener.class);

    private PriceService priceService;

    public PriceListener(PriceService priceService) {
        this.priceService = priceService;
    }

    @StreamListener(value = PriceGatewaySink.INPUT)
    public void listenForPrice(Message<String> pricesInput) {

        logger.info(" received new Price [" + pricesInput.toString() + "] ");
        String[] priceArray = pricesInput.getPayload().split("\n");
        //106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001
        priceService.increasePriceAndSave(priceArray);
    }
}
