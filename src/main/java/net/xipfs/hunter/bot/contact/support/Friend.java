package net.xipfs.hunter.bot.contact.support;

import net.xipfs.hunter.bot.contact.Contact;
import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.message.MessageChain;

/**
 * @author xie hui
 */
public class Friend implements Contact {

    private final long userId;
    private final Bot bot;
    private String nickname;
    private String remark;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public long getUserId() {
        return userId;
    }

    public Friend(long userId, Bot bot) {
        this.userId = userId;
        this.bot = bot;
    }

    @Override
    public int sendMessage(MessageChain messageChain) {
        return this.bot.sendPrivateMessage(this.userId, messageChain);
    }
}
