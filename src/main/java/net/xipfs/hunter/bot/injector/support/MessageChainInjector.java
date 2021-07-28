package net.xipfs.hunter.bot.injector.support;

import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.event.message.MessageEvent;
import net.xipfs.hunter.bot.injector.MessageObjectInjector;
import net.xipfs.hunter.bot.message.MessageChain;

/**
 * @author xie hui
 */
public class MessageChainInjector implements MessageObjectInjector<MessageChain> {
    @Override
    public Class<MessageChain> getType() {
        return MessageChain.class;
    }

    @Override
    public MessageChain getObject(MessageEvent messageEvent, MessageChain messageChain, Bot bot) {
        return messageChain;
    }
}
