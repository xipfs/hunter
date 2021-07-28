package net.xipfs.hunter.bot.contact.support;

import net.xipfs.hunter.bot.contact.Contact;
import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.message.MessageChain;

/**
 * @author xie hui
 */
public class Group implements Contact {

    private final long groupId;
    private final Bot bot;

    public Group(long groupId, Bot bot) {
        this.groupId = groupId;
        this.bot = bot;
    }

    public long getGroupId() {
        return groupId;
    }

    @Override
    public int sendMessage(MessageChain messageChain) {
        return this.bot.sendGroupMessage(this.groupId, messageChain);
    }

    public int sendTempMessage(long userId, MessageChain messageChain) {
        return this.getMember(userId).sendMessage(messageChain);
    }

    public void groupBan() {
        this.bot.groupBan(this.groupId);
    }

    public void groupPardon() {
        this.bot.groupPardon(this.groupId);
    }

    public Member getMember(long userId) {
        return new Member(userId, this.groupId, this.bot);
    }

}
