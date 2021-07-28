package net.xipfs.hunter.thirdpart.nomics;

import java.util.HashMap;
import java.util.Map;

/**
 * description: NomicsUtil <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2021/7/14 15:45 <br>
 */
public class NomicsUtil {
    private static Map<String,String> idsSymbolMap = new HashMap<>();
    static {
        idsSymbolMap.put("MASK", "MASK2");
        idsSymbolMap.put("AXS", "AXS2");
        idsSymbolMap.put("FTT", "FTXTOKEN");
        idsSymbolMap.put("TLM", "TLM2");
        idsSymbolMap.put("GTC", "GITCOIN");
    }
    public static String getId(String symbol){
        return idsSymbolMap.getOrDefault(symbol, symbol);
    }
}
