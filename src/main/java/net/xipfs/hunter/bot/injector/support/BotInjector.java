package net.xipfs.hunter.bot.injector.support;

import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.event.message.MessageEvent;
import net.xipfs.hunter.bot.injector.MessageObjectInjector;
import net.xipfs.hunter.bot.message.MessageChain;

/**
 * @author xie hui
 */
public class BotInjector implements MessageObjectInjector<Bot> {
    @Override
    public Class<Bot> getType() {
        return Bot.class;
    }

    @Override
    public Bot getObject(MessageEvent messageEvent, MessageChain messageChain, Bot bot) {
        return bot;
    }
}
