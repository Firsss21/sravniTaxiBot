package firsov.study.sravnitaxibot.common.service;

import firsov.study.sravnitaxibot.common.bean.TaxiInterface;
import firsov.study.sravnitaxibot.common.model.Coords;
import firsov.study.sravnitaxibot.common.model.Location;
import firsov.study.sravnitaxibot.common.model.citymobil.Price;
import firsov.study.sravnitaxibot.common.model.citymobil.Request;
import firsov.study.sravnitaxibot.common.model.citymobil.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CitimobilTaxiService implements TaxiInterface {

    @Value("#{${taxi.citymobil.url}}")
    private String apiLink;

    @Override
    public int getPrice(Coords start, Coords destination) {
        Response response = getResponse(start, destination);
        if (response.getPrices().size() > 0) {
            return 0;
        } else {
            int lowest = Integer.MAX_VALUE;
            for (Price price : response.getPrices()) {
                if (price.getPrice() < lowest) {
                    lowest = price.getPrice();
                }
            }
            return lowest;
        }
    }
//    @Cacheable(value = "price", key = "{start.latitude, start.longitude, dest.latitude, dest.latitude}")
    public Response getResponse(Coords start, Coords dest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("del_latitude", start.getLatitude());
        personJsonObject.put("del_longitude", start.getLongitude());
        personJsonObject.put("latitude", dest.getLatitude());
        personJsonObject.put("longitude", dest.getLongitude());
        personJsonObject.put("method", "getprice");
        personJsonObject.put("ver", "4.59.0");
        HttpEntity<String> request = new HttpEntity<>(personJsonObject.toString(), headers);
        return restTemplate.postForObject(apiLink, request, Response.class);
    }

}
