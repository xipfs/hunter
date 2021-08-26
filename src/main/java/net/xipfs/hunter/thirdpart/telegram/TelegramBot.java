package net.xipfs.hunter.thirdpart.telegram;

import lombok.extern.slf4j.Slf4j;
import net.xipfs.hunter.service.TimService;
import net.xipfs.hunter.utils.HttpUtil;
import net.xipfs.hunter.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.Time;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.URLEncoder;

/**
 * description: TelegramBot <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2021/8/17 16:17 <br>
 */
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println(update.getMessage().getText());
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            String msg = update.getMessage().getText();
            log.info("msg : "+msg);
            if(msg.startsWith("/f")){
                StringBuilder sb = new StringBuilder();
                String result = TimService.cmds(msg).replaceAll("\"","");
                log.info(result);
                if(StringUtils.isEmpty(result)){
                    return;
                }
                sb.append(result);
                message.setText(sb.toString());
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                return;
            }else if(msg.startsWith("/p")){
                String[] strs = msg.trim().split(" ");
                boolean flag = false;
                try {
                    StringBuilder sb = new StringBuilder();
                    int i = 0;
                    for(String str : strs){
                        if(i == 0){
                            i++;
                            continue;
                        }
                        // 处理特殊请求
                        String topic = TimService.topic(str);
                        if(StringUtils.isNotEmpty(topic)){
                            sb.append(topic);
                            flag = true;
                            break;
                        }
                        // 获取coin详情
                        String coinDetail = TimService.coin(str.trim().toUpperCase());
                        if(StringUtils.isNotEmpty(coinDetail)){
                            sb.append(coinDetail);
                            flag =true;
                        }
                    }
                    if(flag){
                        message.setText(sb.toString());
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{

            }
        }
    }

    @Override
    public String getBotUsername() {
        return "callmedodo_bot";
    }

    @Override
    public String getBotToken() {
        return "1994535524:AAFK97fFu0cr_lZsGo0oS5nzyRJS0C2T_Qg";
    }
}