import com.fx.cloudmessagehandler.CloudMessageHandlerApplication;
import com.fx.cloudmessagehandler.model.Price;
import com.fx.cloudmessagehandler.repository.PriceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CloudMessageHandlerApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class PriceControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PriceRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }


    @Test
    public void getPricesById() throws Exception {
        createData(1001, "USD/GBP", 100.00, 110.00);

        mvc.perform(get("/api/prices?id=1001").contentType(MediaType.APPLICATION_JSON)).andDo(print())
           .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
           .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1)))).andExpect(jsonPath("$[0].id", is(1001)))
           .andExpect(jsonPath("$[0].name", is("USD/GBP"))).andExpect(jsonPath("$[0].bid", is(100.00)))
           .andExpect(jsonPath("$[0].ask", is(110.00))).andExpect(jsonPath("$[0].timeStamp", nullValue()));
    }

    private void createData(int id, String name, double bid, double ask) {
        Price price = new Price();
        price.setId(id);
        price.setName(name);
        price.setBid(bid);
        price.setAsk(ask);
        repository.save(price);
    }
}
