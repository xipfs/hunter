package net.xipfs.hunter.webchat.common;

import net.xipfs.hunter.webchat.client.WechatBotClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;

@Configuration
public class WechatBotConfig {
    /** 微信bot 链接地址 */
    @Value("${wechatBot.url}")
    private String wechatBotUrl;
    /** 微信消息处理地址 */
    public static String wechatMsgServerUrl;

    /** 初始化 */
    @Bean
    public WechatBotClient initWechatBotClient() throws URISyntaxException {
        WechatBotClient botClient = new WechatBotClient(wechatBotUrl);
        // 建立连接
        // botClient.connect();
        return botClient;
    }

    @Value("${wechatBotServer.url}")
    public void setWechatMsgServerUrl(String wechatMsgServerUrl) {
        WechatBotConfig.wechatMsgServerUrl = wechatMsgServerUrl;
    }
}
