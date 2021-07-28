package net.xipfs.hunter.thirdpart.coingecko.example;

import net.xipfs.hunter.thirdpart.coingecko.CoinGeckoApiClient;
import net.xipfs.hunter.thirdpart.coingecko.constant.Currency;
import net.xipfs.hunter.thirdpart.coingecko.impl.CoinGeckoApiClientImpl;

import java.util.Map;

public class SimpleExample {
    public static void main(String[] args) {

        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();

        Map<String, Map<String, Double>> bitcoin = client.getPrice("bitcoin",Currency.USD);

        System.out.println(bitcoin);

        double bitcoinPrice = bitcoin.get("bitcoin").get(Currency.USD);

        System.out.println(bitcoinPrice);
    }
}
