package com.fx.cloudmessagehandler.api;

import com.fx.cloudmessagehandler.model.Price;
import com.fx.cloudmessagehandler.service.PriceService;
import com.fx.cloudmessagehandler.util.PriceTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class PriceController {

    @Autowired
    private PriceService priceService;
    @Autowired
    private PriceTransformer priceTransformer;

    // /prices?id=1002,1003,1004, or /price/1001
    @GetMapping(path = {"/api/prices", "/api/price/{id}"})
    public List<Price> getAllPrices(
        @PathVariable(required = false, name = "id")
            String data,
        @RequestParam(required = false)
            Map<String, String> qparams) {
        if (data == null) {
            String params = qparams.get("id");
            return callService("id", params);
        }
        return Collections.emptyList();
    }


    private List<Price> callService(String a, String params) {
        if (a.equals("id")) {
            List<Integer> idList = priceTransformer.getPriceList(params.split(","));
            return priceService.getPriceList(idList);
        }

        return Collections.emptyList();
    }
}
