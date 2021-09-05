package firsov.study.sravnitaxibot.common.bean;

import firsov.study.sravnitaxibot.common.model.Coords;

public interface TaxiInterface {
    int getPrice(Coords start, Coords destination);
}
