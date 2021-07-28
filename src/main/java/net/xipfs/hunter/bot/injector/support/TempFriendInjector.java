package net.xipfs.hunter.bot.injector.support;

import net.xipfs.hunter.bot.contact.support.TempFriend;
import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.event.message.MessageEvent;
import net.xipfs.hunter.bot.event.message.PrivateMessageEvent;
import net.xipfs.hunter.bot.injector.MessageObjectInjector;
import net.xipfs.hunter.bot.message.MessageChain;

public class TempFriendInjector implements MessageObjectInjector<TempFriend> {
    @Override
    public Class<TempFriend> getType() {
        return TempFriend.class;
    }

    @Override
    public TempFriend getObject(MessageEvent messageEvent, MessageChain messageChain, Bot bot) {
        if (!(messageEvent instanceof PrivateMessageEvent)) {
            return null;
        }
        if (!("group".equals(((PrivateMessageEvent) messageEvent).getSubType()))) {
            return null;
        }
        return new TempFriend(messageEvent.getUserId(), bot);
    }
}
