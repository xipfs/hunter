package net.xipfs.hunter.task;

import com.alibaba.fastjson.JSONArray;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import net.xipfs.hunter.conf.HunterConf;
import net.xipfs.hunter.markets.binance.LongShortRatio;
import net.xipfs.hunter.markets.binance.LongShortUtil;
import net.xipfs.hunter.thirdpart.bitfinex.BitfinexApiClient;
import net.xipfs.hunter.thirdpart.coingecko.CoinGeckoApiClient;
import net.xipfs.hunter.thirdpart.coingecko.domain.Global.Global;
import net.xipfs.hunter.thirdpart.coingecko.impl.CoinGeckoApiClientImpl;
import net.xipfs.hunter.thirdpart.thecoinmonitor.CoinMonitorApiClient;
import net.xipfs.hunter.utils.BinanceTa4jUtil;
import net.xipfs.hunter.utils.BinanceUtil;
import net.xipfs.hunter.utils.HttpUtil;
import net.xipfs.hunter.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.ta4j.core.*;
import org.ta4j.core.num.DecimalNum;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * description: SaticScheduleTask <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2021/7/9 14:03 <br>
 */

@Configuration
@EnableScheduling
@Slf4j
public class StaticScheduleTask {
    private static Map<String,BigDecimal> shortVolumeMap = new HashMap<>();
    private static Map<String,BigDecimal> longVolumeMap = new HashMap<>();
    @Autowired
    private HunterConf conf;

    @Scheduled(cron="0 0 8,12,16,20,0,4 * * ?")
    private void findLuckyCoins() {
        String[] groupIds = conf.getWhiteListGroup().split("#");
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append("****ALL MARKET GOOD COINS****\\n");
        try {
            BinanceUtil.init("LXyty1nDerKp0x9QRMXcW9YCsCbgv0h9HGxNb8C5Ysj7ov6rrSoBSGmjNrOs67Xo", "gZeHmJiRlsbZ8dMgkRIHxkgSGfQLpQOf0vQFRwmsLJ4YOlrqlK6Zrky7SnakvCvk");
            List<String> symbols=BinanceUtil.getBitcoinSymbols();
            for(String symbol:symbols){
                try {
                    log.info("find coin :"+symbol);
                    sb.append(findBestCoin(symbol));
                    log.info("sleep sleep");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("\"");
        String res = HttpUtil.builder()
                .setContentType("application/json; charset=utf-8")
                .ajaxJson("{\"group_id\": 1129451242,\"message\": " + sb.toString() + "}")
                .doPost("http://127.0.0.1:5700//send_group_msg")
                .toJson();
        System.out.println(res);
    }
    private void monitor() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        String monitorStr = CoinMonitorApiClient.monitor();
        if(StringUtils.isEmpty(monitorStr)){
            return;
        }else{
            sb.append(monitorStr);
        }
        sb.append("\"");
        String res = HttpUtil.builder()
                .setContentType("application/json; charset=utf-8")
                .ajaxJson("{\"group_id\": 1129451242,\"message\": " + sb.toString() + "}")
                .doPost("http://127.0.0.1:5700//send_group_msg")
                .toJson();
    }

    @Scheduled(fixedRate = 1000 * 60 * 120)
    private void shortLongVolume() throws Exception {
        DecimalFormat df = new DecimalFormat(",###");
        DecimalFormat df2 = new DecimalFormat(",###.##");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        Date preDate = c.getTime();
        c.add(Calendar.DAY_OF_WEEK, -1);
        Date preWeek = c.getTime();

        String dateStr = sdf.format(date);
        String preDateStr = sdf.format(preDate);
        String preWeekStr = sdf.format(preWeek);

        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append("*********Bitfinex Market*********\\n");
        String shortVolumeStr = BitfinexApiClient.shortVolume();
        JSONArray array = JSONArray.parseArray(shortVolumeStr);
        if(array.size()>0){
            BigDecimal shortVolume = (BigDecimal)array.get(1);
            shortVolumeMap.put(dateStr,shortVolume);
            sb.append("当前空头借币数量: "+df.format(shortVolume)+"\\n");
            if(shortVolumeMap.get("preDateStr")!=null){
                sb.append("1d : ").append(df.format(shortVolumeMap.get("preDateStr").subtract(shortVolume))).append("\\n");
            }
            if(shortVolumeMap.get("preWeekStr")!=null){
                sb.append("7d : ").append(df.format(shortVolumeMap.get("preWeekStr").subtract(shortVolume))).append("\\n");
            }

        }
        String longVolumeStr = BitfinexApiClient.longVolume();
        JSONArray array2 = JSONArray.parseArray(longVolumeStr);
        if(array2.size()>0){
            BigDecimal longVolume = (BigDecimal)array2.get(1);
            longVolumeMap.put(dateStr,longVolume);
            sb.append("当前多头持币数量: "+df.format(longVolume)+"\\n");
            if(longVolumeMap.get("preDateStr")!=null){
                sb.append("1d : ").append(df.format(longVolumeMap.get("preDateStr").subtract(longVolume))).append("\\n");
            }
            if(longVolumeMap.get("preWeekStr")!=null){
                sb.append("7d : ").append(df.format(longVolumeMap.get("preWeekStr").subtract(longVolume))).append("\\n");
            }
        }
        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();
        Double btcd = client.getGlobal().getData().getMarketCapPercentage().get("btc");
        sb.append("btc.d : ").append(df2.format(btcd)).append("%\\n");
        sb.append("\"");
        String res = HttpUtil.builder()
                .setContentType("application/json; charset=utf-8")
                .ajaxJson("{\"group_id\": 1129451242,\"message\": " + sb.toString() + "}")
                .doPost("http://127.0.0.1:5700//send_group_msg")
                .toJson();
        System.out.println(res);
    }
    private static String findBestCoin(String symbol){
        DecimalFormat df = new DecimalFormat("######0.00");
        NumberFormat nf = new DecimalFormat("$,###.####");
        DecimalFormat df2 = new DecimalFormat(",###");
        StringBuilder sb = new StringBuilder();
        List<Candlestick> candlesticks = BinanceUtil.getCandlestickBars(symbol, CandlestickInterval.DAILY);
        symbol = symbol.replace("USDT","");
        if (candlesticks.size() < 10) {
            return "";
        }
        BarSeries barSeries = BinanceTa4jUtil.convertToBarSeries(candlesticks.subList(0, candlesticks.size() - 9), symbol, CandlestickInterval.DAILY.getIntervalId());
        Strategy strategy = BinanceTa4jUtil.buildStrategy(barSeries, "EMA");
        TradingRecord tradingRecord = new BaseTradingRecord();
        Stack<String> stack = new Stack<>();
        for (int i = candlesticks.size() - 9; i < candlesticks.size(); i++) {
            Bar newBar = BinanceTa4jUtil.convertToBaseBar(candlesticks.get(i));
            barSeries.addBar(newBar);
            int endIndex = barSeries.getEndIndex();
            assert strategy != null;
            if (strategy.shouldEnter(endIndex)) {
                boolean entered = tradingRecord.enter(endIndex, newBar.getClosePrice(), DecimalNum.valueOf(10));
                if (entered) {
                    Trade entry = tradingRecord.getLastEntry();
                    ZonedDateTime time = barSeries.getBar(entry.getIndex()).getBeginTime();
                    String timeStr = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time);
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String today  = sdf.format(date);
                    if(timeStr.equals(today)){
                        StringBuilder sb1 = new StringBuilder();
                        double buyPrice = entry.getNetPrice().doubleValue();
                        sb1.append(symbol).
                                append(" 买入信号 (价格: ").
                                append(entry.getNetPrice().doubleValue()).
                                append(" 时间: ").
                                append(timeStr).
                                append(")\\n");
                        stack.push(sb1.toString());
                    }

                }
            } else if (strategy.shouldExit(endIndex)) {
                boolean exited = tradingRecord.exit(endIndex, newBar.getClosePrice(), DecimalNum.valueOf(10));
                if (exited) {
                    Trade exit = tradingRecord.getLastExit();
                    ZonedDateTime time = barSeries.getBar(exit.getIndex()).getBeginTime();
                    String timeStr = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time);
                    double sellPrice = exit.getNetPrice().doubleValue();
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String today  = sdf.format(date);
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(symbol).
                            append(" 卖出信号 (价格: ").
                            append(exit.getNetPrice().doubleValue()).
                            append(" 时间: ").
                            append(timeStr).
                            append(")\\n");
                    if(timeStr.equals(today)) {
                        stack.push(sb1.toString());
                    }
                }
            }
            if(stack.size()>0){
                sb.append(stack.pop());
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("*******Scan Market********\\n");
        sb.append("*******Good Coins*********\\n");
        try {
            BinanceUtil.init("LXyty1nDerKp0x9QRMXcW9YCsCbgv0h9HGxNb8C5Ysj7ov6rrSoBSGmjNrOs67Xo", "gZeHmJiRlsbZ8dMgkRIHxkgSGfQLpQOf0vQFRwmsLJ4YOlrqlK6Zrky7SnakvCvk");
            List<String> symbols=BinanceUtil.getBitcoinSymbols();
            for(String symbol:symbols){
                sb.append(findBestCoin(symbol));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
    }
}
