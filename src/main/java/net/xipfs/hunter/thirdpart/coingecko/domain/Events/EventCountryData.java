package net.xipfs.hunter.thirdpart.coingecko.domain.Events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class EventCountryData {
    @JsonProperty("country")
    private String country;
    @JsonProperty("code")
    private String code;

}
