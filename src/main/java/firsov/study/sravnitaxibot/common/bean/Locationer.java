package firsov.study.sravnitaxibot.common.bean;

import firsov.study.sravnitaxibot.common.model.Coords;

import java.io.IOException;

public interface Locationer {
    Coords getCoords(String location) throws IOException, InterruptedException;
    boolean isRightAddress(String location);
    boolean isCity(String city);
}
