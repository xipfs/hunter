package net.xipfs.hunter.bot.handler;

import com.alibaba.fastjson.JSONObject;
import net.xipfs.hunter.bot.core.Bot;

/**
 * @author xie hui
 */
public interface EventHandler {

    void handle(JSONObject jsonObject, Bot bot);

}
