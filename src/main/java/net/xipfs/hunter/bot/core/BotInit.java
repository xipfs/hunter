package net.xipfs.hunter.bot.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

/**
 * @author xie hui
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BotInit implements CommandLineRunner {

    @Override
    public void run(String... args) {
        BotFactory.initHandlerMethod();
        BotFactory.initBot();
    }
}
