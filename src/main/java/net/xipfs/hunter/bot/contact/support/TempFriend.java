package net.xipfs.hunter.bot.contact.support;

import net.xipfs.hunter.bot.contact.Contact;
import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.message.MessageChain;

/**
 * @author xie hui
 */
public class TempFriend implements Contact {

    private final long userId;
    private final Bot bot;

    public TempFriend(long userId, Bot bot) {
        this.userId = userId;
        this.bot = bot;
    }

    public long getUserId() {
        return this.userId;
    }

    @Override
    public int sendMessage(MessageChain messageChain) {
        return this.bot.sendPrivateMessage(this.userId, messageChain);
    }

}
