package firsov.study.sravnitaxibot.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Location {
    private List<Data> data;

    @Override
    public String toString() {
        return "Location{" +
                "data=" + data +
                '}';
    }
}
