package net.xipfs.hunter;

import net.xipfs.hunter.bot.EnableBot;
import net.xipfs.hunter.cache.CoinCache;
import net.xipfs.hunter.thirdpart.telegram.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @author xiehui6
 */
@SpringBootApplication
public class HunterApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(HunterApplication.class, args);
        CoinCache.initCoins();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
