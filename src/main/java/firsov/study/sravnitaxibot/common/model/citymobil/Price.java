package firsov.study.sravnitaxibot.common.model.citymobil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Price {
    private int price;

    @Override
    public String toString() {
        return "Price{" +
                "price=" + price +
                '}';
    }
}
