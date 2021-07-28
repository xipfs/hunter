package net.xipfs.hunter.bot.contact.support;

import net.xipfs.hunter.bot.contact.Contact;
import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.message.MessageChain;

/**
 * @author xie hui
 */
public class Member implements Contact {

    private final long userId;
    private final long groupId;
    private final Bot bot;

    public Member(long userId, long groupId, Bot bot) {
        this.userId = userId;
        this.groupId = groupId;
        this.bot = bot;
    }

    public long getUserId() {
        return this.userId;
    }

    public String getCardName() {
        return this.bot.getMemberInfo(this.groupId, this.userId).getString("card");
    }

    @Override
    public int sendMessage(MessageChain messageChain) {
        if (this.bot.isFriend(this.userId)) {
            return this.bot.sendPrivateMessage(this.userId, messageChain);
        } else {
            return this.bot.sendTempMessage(this.userId, this.groupId, messageChain);
        }
    }

}
