package net.xipfs.hunter.trade;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import lombok.extern.slf4j.Slf4j;
import net.xipfs.hunter.utils.BinanceTa4jUtil;
import net.xipfs.hunter.utils.BinanceUtil;
import org.ta4j.core.*;
import org.ta4j.core.num.DecimalNum;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Stack;

/**
 * description: TradeUtil <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2021/7/15 10:46 <br>
 */
@Slf4j
public class TradeUtil {
    public static String emaStragy(String symbol, double price) throws Exception {
        log.info("开始量化评估！");
        DecimalFormat df = new DecimalFormat("######0.00");
        NumberFormat nf = new DecimalFormat("$,###.####");
        DecimalFormat df2 = new DecimalFormat(",###");
        BinanceUtil.init("LXyty1nDerKp0x9QRMXcW9YCsCbgv0h9HGxNb8C5Ysj7ov6rrSoBSGmjNrOs67Xo", "gZeHmJiRlsbZ8dMgkRIHxkgSGfQLpQOf0vQFRwmsLJ4YOlrqlK6Zrky7SnakvCvk");
        StringBuilder sb = new StringBuilder();
        sb.append("**********量化评估**********\\n");
        Stack<String> stack = new Stack<>();
        symbol = symbol+"USDT";
        List<Candlestick> candlesticks = BinanceUtil.getCandlestickBars(symbol.toUpperCase(), CandlestickInterval.DAILY);
        int size = candlesticks.size();
        int index = 7;
        if(size > 30){
            if(size > 200){
                index = 90;
            }
        }else{
            sb.append("时间周期太短 !");
            return sb.toString();
        }

        BarSeries barSeries = BinanceTa4jUtil.convertToBarSeries(candlesticks.subList(0, candlesticks.size() - index), symbol, CandlestickInterval.DAILY.getIntervalId());
        Strategy strategy = BinanceTa4jUtil.buildStrategy(barSeries, "EMA");
        TradingRecord tradingRecord = new BaseTradingRecord();
        for (int i = candlesticks.size() - index; i < candlesticks.size(); i++) {
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
                    StringBuilder sb1 = new StringBuilder();
                    double buyPrice = entry.getNetPrice().doubleValue();
                    double earnPrice = price - buyPrice;
                    double drop = (earnPrice/buyPrice)*100;
                    sb1.
                            append(" 买入信号 (价格: ").
                            append(entry.getNetPrice().doubleValue()).
                            append(" 时间: ").
                            append(timeStr).
                            append(" 当前盈利: ").
                            append(nf.format(earnPrice)).
                            append(" 涨幅: ").
                            append(df.format(drop)).append("%").
                            append(")\\n");
                    stack.push(sb1.toString());
                }
            } else if (strategy.shouldExit(endIndex)) {
                boolean exited = tradingRecord.exit(endIndex, newBar.getClosePrice(), DecimalNum.valueOf(10));
                if (exited) {
                    Trade exit = tradingRecord.getLastExit();
                    ZonedDateTime time = barSeries.getBar(exit.getIndex()).getBeginTime();
                    String timeStr = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(time);
                    double sellPrice = exit.getNetPrice().doubleValue();
                    double earnPrice = price - sellPrice;
                    double drop = (earnPrice/sellPrice)*100;
                    StringBuilder sb1 = new StringBuilder();
                    sb1.
                            append(" 卖出信号 (价格: ").
                            append(exit.getNetPrice().doubleValue()).
                            append(" 时间: ").
                            append(timeStr).
                            append(" 避免损失: ").
                            append(nf.format(earnPrice)).
                            append(" 跌幅: ").
                            append(df.format(drop)).append("%").
                            append(")\\n");
                    stack.push(sb1.toString());
                }
            }
        }
        if(stack.size()>0){
            sb.append(stack.pop());
        }else{
            sb.append("当前没有买卖信号！\\n");
        }
        return sb.toString();
    }
    public static void main(String[] args) throws Exception {
        System.out.println(emaStragy("dodo",30));
    }
}
