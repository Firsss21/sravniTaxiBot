package firsov.study.sravnitaxibot.common.model.citymobil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Route {
    private Double duration;
    private Double distance;

    @Override
    public String toString() {
        return "Route{" +
                "duration=" + duration +
                ", distance=" + distance +
                '}';
    }
}
