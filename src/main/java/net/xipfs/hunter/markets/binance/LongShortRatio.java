package net.xipfs.hunter.markets.binance;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 多空比 <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2021/7/9 13:47 <br>
 */
@Data
public class LongShortRatio {
    private String longAccount;
    private String longShortRatio;
    private String shortAccount;
    private String pair;
    private long timestamp;

    @Override
    public String toString() {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strTime = sdf.format(date);
        return "time :" + strTime + " long/short:" + longShortRatio;
    }
}
