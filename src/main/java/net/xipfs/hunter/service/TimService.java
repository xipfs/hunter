package net.xipfs.hunter.service;

import net.xipfs.hunter.bot.annotation.GroupMessageHandler;
import net.xipfs.hunter.bot.contact.support.Group;
import net.xipfs.hunter.bot.contact.support.Member;
import net.xipfs.hunter.bot.event.message.GroupMessageEvent;
import lombok.extern.slf4j.Slf4j;
import net.xipfs.hunter.cache.CoinCache;
import net.xipfs.hunter.conf.HunterConf;
import net.xipfs.hunter.thirdpart.feixiaohao.FeiXiaoHaoApiClient;
import net.xipfs.hunter.thirdpart.oick.OickApiClient;
import net.xipfs.hunter.utils.HttpUtil;
import net.xipfs.hunter.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * description: TimSerivce <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2021/7/12 17:34 <br>
 */
@Service
@Slf4j
public class TimService {
    private static Map<String,String> mapping = new HashMap<>();
    @Autowired
    private HunterConf conf;
    @GroupMessageHandler
    public void listenGroupMsg(Group group, GroupMessageEvent event, Member member, int id, String content) throws UnsupportedEncodingException {
        long groupId = group.getGroupId();
        // 验证白名单群号
        if(!conf.getWhiteListGroup().contains(groupId+"")){
            log.info("该群号不在白名单");
            return;
        }
        log.info("收到群: {}, 消息: {}", groupId , event.getMessage());
        String msg = event.getMessage();
        if(msg.startsWith("#")){
            StringBuilder sb = new StringBuilder();
            String result = cmds(msg).replaceAll("\"","");
            log.info(result);
            if(StringUtils.isEmpty(result)){
                return;
            }
            sb.append(result);
            /**
            String res = HttpUtil.builder()
                    .setContentType("application/json; charset=utf-8")
                    .ajaxJson("{\"group_id\": "+groupId+",\"message\": " + sb.toString() + "}")
                    .doPost("http://127.0.0.1:5700//send_group_msg")
                    .toJson();
            System.out.println(res);
             */
            String res= HttpUtil.builder().doGet("https://api.telegram.org/bot1994535524:AAFK97fFu0cr_lZsGo0oS5nzyRJS0C2T_Qg/sendMessage?chat_id=@gototrading&text="+ URLEncoder.encode(sb.toString(), "UTF-8")).toJson();

            return;
        }
        if(StringUtil.isContainChinese(msg)){
            return;
        }
        String[] strs = msg.trim().split(" ");
        boolean flag = false;
        try {
            StringBuilder sb = new StringBuilder();
            for(String str : strs){
                // 处理特殊请求
                String topic = topic(str);
                if(StringUtils.isNotEmpty(topic)){
                    sb.append(topic);
                    flag = true;
                    break;
                }
                // 获取coin详情
                String coinDetail = coin(str.trim().toUpperCase());
                if(StringUtils.isNotEmpty(coinDetail)){
                    sb.append(coinDetail);
                    flag =true;
                }
            }
            if(flag){
                /** String res = HttpUtil.builder()
                        .setContentType("application/json; charset=utf-8")
                        .ajaxJson("{\"group_id\": "+groupId+",\"message\": " + sb.toString() + "}")
                        .doPost("http://127.0.0.1:5700//send_group_msg")
                        .toJson();
                System.out.println(res);*/
                String res= HttpUtil.builder().doGet("https://api.telegram.org/bot1994535524:AAFK97fFu0cr_lZsGo0oS5nzyRJS0C2T_Qg/sendMessage?chat_id=@gototrading&text="+ URLEncoder.encode(sb.toString(), "UTF-8")).toJson();

            }
        } catch (Exception e) {
            e.printStackTrace();
            /**
            String result = HttpUtil.builder()
                    .setContentType("application/json; charset=utf-8")
                    .ajaxJson("{\"group_id\": 1129451242,\"message\": \"找不到该币种\"}")
                    .doPost("http://127.0.0.1:5700//send_group_msg")
                    .toJson();*/
            String res= HttpUtil.builder().doGet("https://api.telegram.org/bot1994535524:AAFK97fFu0cr_lZsGo0oS5nzyRJS0C2T_Qg/sendMessage?chat_id=@gototrading&text="+ URLEncoder.encode("未找到该币种", "UTF-8")).toJson();

        }
    }
    public static String topic(String topic){
        boolean flag = false;
        StringBuilder sb  = new StringBuilder();
        if(topic.toUpperCase().equals("HOT")){
            flag =true;
            sb.append("*************HOT COIN************\n");
            String hotCoins = FeiXiaoHaoApiClient.hot();
            sb.append(hotCoins).append("\n");
            sb.append("***********************************\n");
        }
        if(topic.toUpperCase().equals("SBF")){
            flag =true;
            sb.append("*************SBF COIN************\n");
            String hotCoins = FeiXiaoHaoApiClient.concept(55);
            sb.append(hotCoins).append("\n");
            sb.append("***********************************\n");
        }
        if(topic.toUpperCase().equals("BSC")){
            flag =true;
            sb.append("*************BSC COIN************\n");
            String hotCoins = FeiXiaoHaoApiClient.concept(53);
            sb.append(hotCoins).append("\n");
            sb.append("***********************************\n");
        }
        if(topic.toUpperCase().equals("NFT")){
            flag =true;
            sb.append("*************NFT COIN************\n");
            String hotCoins = FeiXiaoHaoApiClient.concept(41);
            sb.append(hotCoins).append("\n");
            sb.append("***********************************\n");
        }
        if(flag){
            return sb.toString();
        }else{
            return null;
        }
    }
    public static String coin(String coin) throws Exception {
        StringBuilder sb = new StringBuilder();
        if(CoinCache.coinsMap.containsKey(coin)){
            String newCoin = CoinCache.coinsMap.get(coin);
            String result = FeiXiaoHaoApiClient.coin(coin,newCoin);
            sb.append(result);
        }
        return sb.toString();
    }
    public static String cmds(String msg) {
        StringBuilder sb = new StringBuilder();
        if(msg.contains("dog")){
            try {
                sb.append(OickApiClient.dog()).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(msg.contains("du")){
            try {
                sb.append(OickApiClient.du()).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(msg.contains("yulu")){
            try {
                sb.append(OickApiClient.yulu()).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info(sb.toString());
        return sb.toString();
    }
    public static void main(String[] args) throws Exception {
        CoinCache.initCoins();
        System.out.println(coin("MIR"));
    }
}
