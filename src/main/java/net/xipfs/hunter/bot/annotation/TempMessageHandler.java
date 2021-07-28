package net.xipfs.hunter.bot.annotation;

import java.lang.annotation.*;

/**
 * @author xie hui
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TempMessageHandler {

    /**
     * 匹配正则
     */
    String regex() default "none";

    /**
     * 限制发言人
     */
    long senderId() default 0;
}
