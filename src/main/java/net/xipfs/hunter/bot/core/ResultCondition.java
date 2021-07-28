package net.xipfs.hunter.bot.core;

import lombok.Data;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author xie hui
 */
@Data
public class ResultCondition {

    private Lock lock;

    private Condition condition;

}
