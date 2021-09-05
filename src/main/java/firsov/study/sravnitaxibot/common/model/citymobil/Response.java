package firsov.study.sravnitaxibot.common.model.citymobil;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Response {
    private List<Price> prices;
    private Route route;


    @JsonProperty("expires_at")
    private Long expires;

    @Override
    public String toString() {
        return "Response{" +
                "prices=" + prices +
                "route=" + route +
                ", expires=" + expires +
                '}';
    }
}
