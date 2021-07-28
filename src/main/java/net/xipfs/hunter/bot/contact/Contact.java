package net.xipfs.hunter.bot.contact;

import net.xipfs.hunter.bot.message.Message;
import net.xipfs.hunter.bot.message.MessageChain;

/**
 * @author xie hui
 */
public interface Contact {

    default int sendMessage(Message message) {
        MessageChain messageChain = new MessageChain();
        messageChain.add(message);
        return this.sendMessage(messageChain);
    }

    int sendMessage(MessageChain messageChain);

}
