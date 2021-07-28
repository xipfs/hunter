package net.xipfs.hunter.bot.handler.message;

import com.alibaba.fastjson.JSONObject;
import net.xipfs.hunter.bot.annotation.GroupMessageHandler;
import net.xipfs.hunter.bot.contact.support.Group;
import net.xipfs.hunter.bot.core.Bot;
import net.xipfs.hunter.bot.core.BotFactory;
import net.xipfs.hunter.bot.core.HandlerMethod;
import net.xipfs.hunter.bot.event.message.GroupMessageEvent;
import net.xipfs.hunter.bot.handler.EventHandler;
import net.xipfs.hunter.bot.message.Message;
import net.xipfs.hunter.bot.message.MessageChain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xie hui
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupMessageEventHandler implements EventHandler {

    @Override
    public void handle(JSONObject jsonObject, Bot bot) {
        if (!GroupMessageEvent.isSupport(jsonObject)) {
            return;
        }
        GroupMessageEvent groupMessageEvent = jsonObject.toJavaObject(GroupMessageEvent.class);
        MessageChain messageChain = new MessageChain();
        Message message = new Message() {
            @Override
            public String toMessageString() {
                return groupMessageEvent.getMessage();
            }
        };
        messageChain.add(message);
        log.debug(messageChain.toMessageString());
        List<HandlerMethod> handlerMethodList = BotFactory.getHandlerMethodList("bot");
        if (handlerMethodList == null || handlerMethodList.isEmpty()) {
            return;
        }
        Set<HandlerMethod> handlerMethodSet = handlerMethodList.stream().filter(handlerMethod -> {
            if (!handlerMethod.getMethod().isAnnotationPresent(GroupMessageHandler.class)) {
                return false;
            }
            GroupMessageHandler groupMessageHandler = handlerMethod.getMethod().getAnnotation(GroupMessageHandler.class);
            if (groupMessageHandler.groupId() != 0 && groupMessageHandler.groupId() != groupMessageEvent.getGroupId()) {
                return false;
            }
            if (groupMessageHandler.senderId() != 0 && groupMessageHandler.senderId() != groupMessageEvent.getUserId()) {
                return false;
            }
            return groupMessageHandler.regex().equals("none") || messageChain.toString().matches(groupMessageHandler.regex());
        }).collect(Collectors.toSet());
        List<Object> resultList = BotFactory.handleMethod(handlerMethodSet, groupMessageEvent, messageChain, bot);
        for (Object result : resultList) {
            if (result instanceof Message) {
                new Group(groupMessageEvent.getGroupId(), bot).sendMessage((Message) result);
            }
            if (result instanceof MessageChain) {
                new Group(groupMessageEvent.getGroupId(), bot).sendMessage((MessageChain) result);
            }
        }
    }

}
