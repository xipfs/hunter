package net.xipfs.hunter.bot.injector.support;

import net.xipfs.hunter.bot.contact.support.Member;
import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.event.message.GroupMessageEvent;
import net.xipfs.hunter.bot.event.message.MessageEvent;
import net.xipfs.hunter.bot.injector.MessageObjectInjector;
import net.xipfs.hunter.bot.message.MessageChain;

/**
 * @author xie hui
 */
public class MemberInjector implements MessageObjectInjector<Member> {
    @Override
    public Class<Member> getType() {
        return Member.class;
    }

    @Override
    public Member getObject(MessageEvent messageEvent, MessageChain messageChain, Bot bot) {
        if (!(messageEvent instanceof GroupMessageEvent)) {
            return null;
        }
        return new Member(messageEvent.getUserId(), ((GroupMessageEvent) messageEvent).getGroupId(), bot);
    }
}
