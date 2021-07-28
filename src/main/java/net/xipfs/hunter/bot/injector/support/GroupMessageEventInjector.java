package net.xipfs.hunter.bot.injector.support;

import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.event.message.GroupMessageEvent;
import net.xipfs.hunter.bot.event.message.MessageEvent;
import net.xipfs.hunter.bot.injector.MessageObjectInjector;
import net.xipfs.hunter.bot.message.MessageChain;

/**
 * @author xie hui
 */
public class GroupMessageEventInjector implements MessageObjectInjector<GroupMessageEvent> {
    @Override
    public Class<GroupMessageEvent> getType() {
        return GroupMessageEvent.class;
    }

    @Override
    public GroupMessageEvent getObject(MessageEvent messageEvent, MessageChain messageChain, Bot bot) {
        if (!(messageEvent instanceof GroupMessageEvent)) {
            return null;
        }
        return (GroupMessageEvent) messageEvent;
    }
}
