package firsov.study.sravnitaxibot.common.bean;

import firsov.study.sravnitaxibot.common.model.Coords;

public interface TaxiInterface {

    String getName();

    int getPrice(Coords start, Coords destination);
}
