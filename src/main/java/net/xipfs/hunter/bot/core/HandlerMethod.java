package net.xipfs.hunter.bot.core;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author xie hui
 */
@Data
public class HandlerMethod {
    private Class<?> type;
    private Object object;
    private Method method;
}
