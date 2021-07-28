package net.xipfs.hunter.bot.config;

import lombok.Data;

/**
 * @author xie hui
 */
@Data
public class BotConfig {

    private String botName;

    private String websocketUrl;

    private String accessToken;

}
