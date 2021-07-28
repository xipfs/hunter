package net.xipfs.hunter.bot.support;

import net.xipfs.hunter.bot.core.*;
import net.xipfs.hunter.bot.core.BotDispatcher;
import net.xipfs.hunter.bot.core.BotFactory;
import net.xipfs.hunter.bot.core.BotInit;
import net.xipfs.hunter.bot.core.SnowFlakeIdGenerator;
import net.xipfs.hunter.bot.handler.message.GroupMessageEventHandler;
import net.xipfs.hunter.bot.handler.message.PrivateMessageEventHandler;
import net.xipfs.hunter.bot.handler.meta.HeartbeatEventHandler;
import net.xipfs.hunter.bot.injector.support.*;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author xie hui
 */
public class BotApplicationRegistrar implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                BotFactory.class.getName(),
                BotDispatcher.class.getName(),
                SnowFlakeIdGenerator.class.getName(),
                HeartbeatEventHandler.class.getName(),
                PrivateMessageEventHandler.class.getName(),
                GroupMessageEventHandler.class.getName(),
                BotInit.class.getName(),
                MessageStringInjector.class.getName(),
                GroupInjector.class.getName(),
                MessageChainInjector.class.getName(),
                TempFriendInjector.class.getName(),
                MemberInjector.class.getName(),
                GroupMessageEventInjector.class.getName(),
                MessageIdInjector.class.getName(),
                MessageIdIntInjector.class.getName(),
                BotInjector.class.getName(),
        };
    }

}
