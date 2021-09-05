package firsov.study.sravnitaxibot.common.model;

import lombok.Getter;

@Getter
public class Coords {
    private double latitude;
    private double longitude;

    public Coords(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
