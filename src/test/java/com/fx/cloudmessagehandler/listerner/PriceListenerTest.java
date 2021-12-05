package com.fx.cloudmessagehandler.listerner;

import com.fx.cloudmessagehandler.CloudMessageHandlerApplication;
import com.fx.cloudmessagehandler.messaging.PriceGatewaySink;
import com.fx.cloudmessagehandler.model.Price;
import com.fx.cloudmessagehandler.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = CloudMessageHandlerApplication.class, properties = {
    "--spring.cloud.stream.bindings.input.contentType=text/plain",
    "--spring.cloud.stream.bindings.output.contentType=text/plain"})
@DirtiesContext
@AutoConfigureTestDatabase
class PriceListenerTest {

    @Value("${com.santander.priceFactor}")
    String priceFactor;

    @Autowired
    private PriceRepository repository;

    @Autowired
    PriceGatewaySink priceGatewaySink;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    static Stream<Arguments> messageData() {
        return Stream.of(Arguments
                             .of("Single Message", "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001",
                                 new int[]{106}, new double[]{2.1}, new double[]{2.2}, new String[]{ "EUR/USD"}
                                ),
                        Arguments.of("Multiple Messages",
                                        "107, EUR/USD, 2.1000,1.2000,01-06-2020 12:01:01:001 \n108, EUR/USD, 2.1000,1.2000,01-06-2020 12:01:01:001",
                                        new int[]{107, 108}, new double[]{3.1, 3.1}, new double[]{2.2, 2.2}, new String[]{"EUR/USD", "EUR/USD"}
                                    )
                        );
    }

    @ParameterizedTest
    @MethodSource("messageData")
    void listenForPrice(String testName, String messageString, int[] id, double[] bid, double[] ask, String[] name) {
        Message<String> message =
            MessageBuilder.createMessage(messageString, new MessageHeaders(new HashMap<String, Object>()));
        priceGatewaySink.input().send(message);

        for (int j=0; j < id.length; j++) {
            Price priceRow = repository.findById(id[j]).get();
            assertThat(priceRow).extracting("id", "bid", "ask", "name")
                                .containsSequence(id[j], bid[j], ask[j], name[j]);
        }

        System.out.println("HareKrishna");
    }
}