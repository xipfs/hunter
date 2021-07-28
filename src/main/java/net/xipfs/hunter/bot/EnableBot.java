package net.xipfs.hunter.bot;

import net.xipfs.hunter.bot.support.BotApplicationRegistrar;
import net.xipfs.hunter.bot.support.BotAutoConfigRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xie hui
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({BotApplicationRegistrar.class, BotAutoConfigRegistrar.class})
public @interface EnableBot {
}
