package net.xipfs.hunter.bot.handler.meta;

import com.alibaba.fastjson.JSONObject;
import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.event.meta.HeartbeatEvent;
import net.xipfs.hunter.bot.handler.EventHandler;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xie hui
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HeartbeatEventHandler implements EventHandler {

    @Override
    public void handle(JSONObject jsonObject, Bot bot) {
        if (!HeartbeatEvent.isSupport(jsonObject)) {
            return;
        }
        bot.getBotClient().getChannel().writeAndFlush(new PingWebSocketFrame());
        HeartbeatEvent heartbeatEvent = jsonObject.toJavaObject(HeartbeatEvent.class);
        log.debug("heartbeat-event: " + heartbeatEvent);
    }
}
