package net.xipfs.hunter.bot.api.support;

import com.alibaba.fastjson.annotation.JSONField;
import net.xipfs.hunter.bot.api.BaseApi;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xie hui
 */
public class DeleteMsg extends BaseApi {

    private final Param param;

    public DeleteMsg(long messageId) {
        this.param = new Param();
        this.param.setMessageId(messageId);
    }

    @Override
    public String getAction() {
        return "delete_msg";
    }

    @Override
    public Object getParams() {
        return param;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Param {
        @JSONField(name = "message_id")
        private long messageId;

    }
}
