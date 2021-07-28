package net.xipfs.hunter;

import net.xipfs.hunter.bot.EnableBot;
import net.xipfs.hunter.cache.CoinCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xiehui6
 */
@SpringBootApplication
@EnableBot
public class HunterApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(HunterApplication.class, args);
        CoinCache.initCoins();
    }
}
