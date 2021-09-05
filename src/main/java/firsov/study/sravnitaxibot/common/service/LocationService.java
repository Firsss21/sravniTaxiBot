package firsov.study.sravnitaxibot.common.service;

import firsov.study.sravnitaxibot.common.bean.Locationer;
import firsov.study.sravnitaxibot.common.model.Coords;
import firsov.study.sravnitaxibot.common.model.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class LocationService implements Locationer {

    @Value("#{${locationeer.url}}")
    private String apiLink;

    @Cacheable(value = "location")
    public Location getLocation(String location) {
        RestTemplate restTemplate = new RestTemplate();
        Location loc = restTemplate.getForObject(apiLink.replace("{loc}", location), Location.class);
        return loc;
    }

    // cache
    @Override
    public Coords getCoords(String location) throws IOException, InterruptedException {
        Location loc = getLocation(location);
        return new Coords(loc.getData().get(0).getLatitude(), loc.getData().get(0).getLongitude());

    }

    // cache
    @Override
    public boolean isRightAddress(String loc) {
        Location location = getLocation(loc);
        return location.getData().size() > 0;
    }

    // cache
    @Override
    public boolean isCity(String city) {
        Location location = getLocation(city);
        return location.getData().size() > 0;
    }
}
