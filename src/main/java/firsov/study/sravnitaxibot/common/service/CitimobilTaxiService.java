package firsov.study.sravnitaxibot.common.service;

import firsov.study.sravnitaxibot.common.bean.TaxiInterface;
import firsov.study.sravnitaxibot.common.model.Coords;
import firsov.study.sravnitaxibot.common.model.citymobil.Price;
import firsov.study.sravnitaxibot.common.model.citymobil.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CitimobilTaxiService implements TaxiInterface {

    @Value("#{${taxi.citymobil.url}}")
    private String apiLink;

    @Override
    public int getPrice(Coords start, Coords destination) {
        Response response = getResponse(start, destination);
        System.out.println(response);
        if (response.getPrices().size() <= 0) {
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
        personJsonObject.put("del_latitude", dest.getLatitude());
        personJsonObject.put("del_longitude", dest.getLongitude());
        personJsonObject.put("latitude", start.getLatitude());
        personJsonObject.put("longitude", start.getLongitude());
        personJsonObject.put("method", "getprice");
        personJsonObject.put("tariff_group", new int[]{2, 4, 13, 7, 5});
        personJsonObject.put("ver", "4.59.0");
        HttpEntity<String> req = new HttpEntity<>(personJsonObject.toString());
        HttpEntity<String> request = new HttpEntity<>(personJsonObject.toString(), headers);
        System.out.println(request);
        return restTemplate.postForObject(apiLink, request, Response.class);
    }


    @Override
    public String getName() {
        return "Ситимобил";
    }
}
