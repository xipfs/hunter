package net.xipfs.hunter.bot.message.support;

import com.alibaba.fastjson.JSON;
import net.xipfs.hunter.bot.message.Message;
import lombok.Data;

/**
 * @author xie hui
 */
@Data
public class FaceMessage implements Message {

    private String id;

    @Override
    public String toString() {
        return "face[" + id + "]";
    }

    @Override
    public String toMessageString() {
        return String.format("{\"type\":\"%s\",\"data\":%s}", "face", JSON.toJSONString(this));
    }

}
