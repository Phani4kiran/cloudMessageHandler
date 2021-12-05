package com.fx.cloudmessagehandler.util;

import com.fx.cloudmessagehandler.model.Price;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PriceTransformer {

    public List<Price> toListPrice(String[] priceArray) {
        List<Price> priceList = Arrays.stream(priceArray).sequential().map(p -> {
            Price price = new Price();
            String[] priceLine = p.split(",");
            price.setId(Integer.parseInt(priceLine[0]));
            price.setName(priceLine[1].trim());
            price.setBid(Double.parseDouble(priceLine[2]));
            price.setAsk(Double.parseDouble(priceLine[3]));
            price.setTimeStamp(LocalDateTime.parse(priceLine[4].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS")));
            return price;
        }).collect(Collectors.toList());

        return priceList;
    }

    public List<Integer> getPriceList(String[] ids) {
        return Arrays.stream(ids).map(Integer::parseInt).collect(Collectors.toList());
    }
}
