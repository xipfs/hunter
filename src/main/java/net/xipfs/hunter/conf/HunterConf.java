package net.xipfs.hunter.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * description: HunterConf <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2021/7/12 14:07 <br>
 */
@Configuration
@Data
public class HunterConf {
    @Value("${thread.pauseTimeInMinutes}")
    private int pauseTimeInMinutes;
    @Value("${tick.timeFrame}")
    private String timeFrame;
    @Value("${trading.doTrades}")
    private boolean doTrades;
    @Value("${trading.stopLossPercentage}")
    private double stopLossPercentage;
    @Value("${trading.tradeSizeBTC}")
    private double tradeSizeBTC;
    @Value("${trading.maxSimultaneousTrades}")
    private int maxSimultaneousTrades;
    @Value("${trading.doTrailingStop}")
    private boolean doTrailingStop;
    @Value("${trading.strategy}")
    private String strategy;
    @Value("${bot.websocketUrl}")
    private String websocketUrl;
    @Value("${bot.whiteListGroup}")
    private String whiteListGroup;
}
