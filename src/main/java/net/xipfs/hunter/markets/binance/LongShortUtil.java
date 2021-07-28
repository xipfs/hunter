package net.xipfs.hunter.markets.binance;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.xipfs.hunter.utils.HttpUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * description: BinanceUtil <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2021/7/9 13:40 <br>
 */
public class LongShortUtil {
    public static String long_short_ratio() {
        String url = "http://www.binancezh.com/bapi/futures/v1/public/delivery/data/topLongShortAccountRatio?pair=BTCUSD&period=1h&contractType=ALL";
        String longShortJson = HttpUtil.builder()
                .setContentType("application/json;charset=UTF-8")
                .doGet(url)
                .toJson();
        return longShortJson;
    }

    public static void main(String[] args) {
        String longShortJson = long_short_ratio();
        Gson gson = new Gson();
        Type longShortListType = new TypeToken<ArrayList<LongShortRatio>>() {
        }.getType();
        List<LongShortRatio> longShortList = gson.fromJson(longShortJson, longShortListType);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (LongShortRatio longShortRatio : longShortList) {
            if (i > 5) {
                break;
            }
            sb.append(longShortRatio).append("\n");
            i++;
        }
        System.out.println(sb.toString());
    }
}
