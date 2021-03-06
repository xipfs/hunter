package net.xipfs.hunter.bot.handler.message;

import com.alibaba.fastjson.JSONObject;
import net.xipfs.hunter.bot.annotation.FriendMessageHandler;
import net.xipfs.hunter.bot.annotation.TempMessageHandler;
import net.xipfs.hunter.bot.contact.support.TempFriend;
import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.core.BotFactory;
import net.xipfs.hunter.bot.core.HandlerMethod;
import net.xipfs.hunter.bot.event.message.PrivateMessageEvent;
import net.xipfs.hunter.bot.handler.EventHandler;
import net.xipfs.hunter.bot.message.Message;
import net.xipfs.hunter.bot.message.MessageChain;
import net.xipfs.hunter.bot.message.MessageTypeHandle;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xie hui
 */
@Slf4j
public class PrivateMessageEventHandler implements EventHandler {
    @Override
    public void handle(JSONObject jsonObject, Bot bot) {
        if (!PrivateMessageEvent.isSupport(jsonObject)) {
            return;
        }
        PrivateMessageEvent privateMessageEvent = jsonObject.toJavaObject(PrivateMessageEvent.class);
        MessageChain messageChain = new MessageChain();
        for (int i = 0; i < privateMessageEvent.getMessage().size(); i++) {
            messageChain.add(MessageTypeHandle.getMessage(privateMessageEvent.getMessage().getJSONObject(i)));
        }
        log.debug(messageChain.toMessageString());
        Set<HandlerMethod> handlerMethodSet;
        if ("group".equals(privateMessageEvent.getSubType())) {
            List<HandlerMethod> handlerMethodList = BotFactory.getHandlerMethodList("bot");
            if (handlerMethodList == null || handlerMethodList.isEmpty()) {
                return;
            }
            handlerMethodSet = handlerMethodList.stream().filter(handlerMethod -> {
                if (!handlerMethod.getMethod().isAnnotationPresent(TempMessageHandler.class)) {
                    return false;
                }
                TempMessageHandler tempMessageHandler = handlerMethod.getMethod().getAnnotation(TempMessageHandler.class);
                if (tempMessageHandler.senderId() != 0 && tempMessageHandler.senderId() != privateMessageEvent.getUserId()) {
                    return false;
                }
                return tempMessageHandler.regex().equals("none") || messageChain.toString().matches(tempMessageHandler.regex());
            }).collect(Collectors.toSet());
        } else if ("friend".equals(privateMessageEvent.getSubType())) {
            List<HandlerMethod> handlerMethodList = BotFactory.getHandlerMethodList("bot");
            if (handlerMethodList == null || handlerMethodList.isEmpty()) {
                return;
            }
            handlerMethodSet = handlerMethodList.stream().filter(handlerMethod -> {
                if (!handlerMethod.getMethod().isAnnotationPresent(FriendMessageHandler.class)) {
                    return false;
                }
                FriendMessageHandler friendMessageHandler = handlerMethod.getMethod().getAnnotation(FriendMessageHandler.class);
                if (friendMessageHandler.senderId() != 0 && friendMessageHandler.senderId() != privateMessageEvent.getUserId()) {
                    return false;
                }
                return friendMessageHandler.regex().equals("none") || messageChain.toString().matches(friendMessageHandler.regex());
            }).collect(Collectors.toSet());
        } else {
            return;
        }
        List<Object> resultList = BotFactory.handleMethod(handlerMethodSet, privateMessageEvent, messageChain, bot);
        for (Object result : resultList) {
            if (result instanceof Message) {
                new TempFriend(privateMessageEvent.getUserId(), bot).sendMessage((Message) result);
            }
            if (result instanceof MessageChain) {
                new TempFriend(privateMessageEvent.getUserId(), bot).sendMessage((MessageChain) result);
            }
        }
    }
}
