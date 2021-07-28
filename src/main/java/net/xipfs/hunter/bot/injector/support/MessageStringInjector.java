package net.xipfs.hunter.bot.injector.support;

import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.event.message.MessageEvent;
import net.xipfs.hunter.bot.injector.MessageObjectInjector;
import net.xipfs.hunter.bot.message.MessageChain;

/**
 * @author xie hui
 */
public class MessageStringInjector implements MessageObjectInjector<String> {
    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public String getObject(MessageEvent messageEvent, MessageChain messageChain, Bot bot) {
        return messageChain.toString();
    }
}
