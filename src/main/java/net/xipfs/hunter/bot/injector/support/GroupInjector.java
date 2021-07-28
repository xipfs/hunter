package net.xipfs.hunter.bot.injector.support;

import net.xipfs.hunter.bot.contact.support.Group;
import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.event.message.GroupMessageEvent;
import net.xipfs.hunter.bot.event.message.MessageEvent;
import net.xipfs.hunter.bot.injector.MessageObjectInjector;
import net.xipfs.hunter.bot.message.MessageChain;

/**
 * @author xie hui
 */
public class GroupInjector implements MessageObjectInjector<Group> {
    @Override
    public Class<Group> getType() {
        return Group.class;
    }

    @Override
    public Group getObject(MessageEvent messageEvent, MessageChain messageChain, Bot bot) {
        if (!(messageEvent instanceof GroupMessageEvent)) {
            return null;
        }
        return new Group(((GroupMessageEvent) messageEvent).getGroupId(), bot);
    }
}
