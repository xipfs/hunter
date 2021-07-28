package net.xipfs.hunter.thirdpart.coingecko.example;

import net.xipfs.hunter.thirdpart.coingecko.CoinGeckoApiClient;
import net.xipfs.hunter.thirdpart.coingecko.domain.Global.Global;
import net.xipfs.hunter.thirdpart.coingecko.impl.CoinGeckoApiClientImpl;


public class GlobalExample {
    public static void main(String[] args) {

        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();

        Global global = client.getGlobal();

        System.out.println(global);

        long markets = global.getData().getMarkets();
        System.out.println(markets);

        long activeCryptoCurrencies = global.getData().getActiveCryptocurrencies();
        System.out.println(activeCryptoCurrencies);

    }
}
