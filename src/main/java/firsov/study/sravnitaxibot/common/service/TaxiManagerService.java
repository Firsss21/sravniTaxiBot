package firsov.study.sravnitaxibot.common.service;

import firsov.study.sravnitaxibot.common.bean.TaxiInterface;
import firsov.study.sravnitaxibot.common.model.Coords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaxiManagerService {
    @Autowired
    private List<TaxiInterface> taxiList;

    public String getPrices(Coords start, Coords dest) {
        String price = "";
        for (TaxiInterface taxi : taxiList) {
            price += taxi.getName() + ": " + taxi.getPrice(start, dest) + " руб \n";
        }
        return price;
    }
}
