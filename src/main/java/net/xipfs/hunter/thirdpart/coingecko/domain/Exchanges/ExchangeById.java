package net.xipfs.hunter.thirdpart.coingecko.domain.Exchanges;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.xipfs.hunter.thirdpart.coingecko.domain.Shared.Ticker;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExchangeById extends Exchanges{
    @JsonProperty("tickers")
    private List<Ticker> tickers;
    @JsonProperty("status_updates")
    private List<Object> statusUpdates;

}
