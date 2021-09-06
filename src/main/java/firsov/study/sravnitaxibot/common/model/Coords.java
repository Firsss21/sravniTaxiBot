package firsov.study.sravnitaxibot.common.model;

import firsov.study.sravnitaxibot.common.entity.Location;
import lombok.Getter;

@Getter
public class Coords {
    private double latitude;
    private double longitude;

    public Coords(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Coords(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }
}
