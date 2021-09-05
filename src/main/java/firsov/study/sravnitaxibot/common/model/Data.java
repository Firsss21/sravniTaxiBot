package firsov.study.sravnitaxibot.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Data {

    private Integer postal_code;

    private Double latitude;
    private Double longitude;

//    @JsonProperty("feels_like")
    private String short_address;

    @Override
    public String toString() {
        return "Data{" +
                "postal_code=" + postal_code +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", short_address='" + short_address + '\'' +
                '}';
    }
}
