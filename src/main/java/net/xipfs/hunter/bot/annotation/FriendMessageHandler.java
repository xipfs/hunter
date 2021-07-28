package net.xipfs.hunter.bot.annotation;

import java.lang.annotation.*;

/**
 * 处理好友消息
 * @author xie hui
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FriendMessageHandler {
    /**
     * 匹配正则
     */
    String regex() default "none";

    /**
     * 限制发言人
     */
    long senderId() default 0;

}
