package com.fx.cloudmessagehandler.service;

import com.fx.cloudmessagehandler.model.Price;
import com.fx.cloudmessagehandler.repository.PriceRepository;
import com.fx.cloudmessagehandler.util.PriceTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {
    @Autowired
    private PriceRepository priceRepo;

    @Value("${com.santander.priceFactor}")
    Double priceFactor;

    @Autowired
    private PriceTransformer priceTransformer;

    public void increasePriceAndSave(String[] prices) {
        List<Price> priceList = priceTransformer.toListPrice(prices);
        priceList.stream().forEach(p -> increasePrice(p, priceFactor));
        priceRepo.saveAll(priceList);
    }

    private void increasePrice(Price p, Double value) {
        p.setAsk(p.getAsk() + value);
        p.setBid(p.getBid() + value);
    }

    public List<Price> getPriceList(List<Integer> iDList) {
        return (List<Price>) priceRepo.findAllById(iDList);
    }


}
