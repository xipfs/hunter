package net.xipfs.hunter.bot.injector.support;

import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.event.message.MessageEvent;
import net.xipfs.hunter.bot.injector.MessageObjectInjector;
import net.xipfs.hunter.bot.message.MessageChain;

/**
 * @author xie hui
 */
public class MessageIdInjector implements MessageObjectInjector<Integer> {
    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public Integer getObject(MessageEvent messageEvent, MessageChain messageChain, Bot bot) {
        return messageEvent.getMessageId();
    }
}
