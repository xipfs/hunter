package net.xipfs.hunter.bot.api;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class ApiResult {

    private String status;

    @JSONField(name = "retcode")
    private int retCode;

    private Object data;

    private String echo;

}
