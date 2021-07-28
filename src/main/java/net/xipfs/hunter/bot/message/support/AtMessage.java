package net.xipfs.hunter.bot.message.support;

import com.alibaba.fastjson.JSON;
import net.xipfs.hunter.bot.message.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xie hui
 */
@Data
@NoArgsConstructor
public class AtMessage implements Message {

    private String qq;

    public AtMessage(String qq) {
        this.qq = qq;
    }

    @Override
    public String toString() {
        return "@" + qq + " ";
    }

    @Override
    public String toMessageString() {
        return String.format("{\"type\":\"%s\",\"data\":%s}", "at", JSON.toJSONString(this));
    }

}
