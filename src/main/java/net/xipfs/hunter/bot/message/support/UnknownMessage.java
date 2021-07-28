package net.xipfs.hunter.bot.message.support;

import net.xipfs.hunter.bot.message.Message;
import lombok.Data;

/**
 * @author xie hui
 */
@Data
public class UnknownMessage implements Message {

    private String json;

    @Override
    public String toString() {
        return "json[" + json + "]";
    }

    @Override
    public String toMessageString() {
        return this.json;
    }
}
