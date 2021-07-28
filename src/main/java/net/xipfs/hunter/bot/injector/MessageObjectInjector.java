package net.xipfs.hunter.bot.injector;

import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.event.message.MessageEvent;
import net.xipfs.hunter.bot.message.MessageChain;

/**
 * @author xie hui
 */
public interface MessageObjectInjector<T> {

    Class<T> getType();

    T getObject(MessageEvent messageEvent, MessageChain messageChain, Bot bot);

}