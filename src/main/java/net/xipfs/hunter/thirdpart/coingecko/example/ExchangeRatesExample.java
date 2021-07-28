package net.xipfs.hunter.thirdpart.coingecko.example;

import net.xipfs.hunter.thirdpart.coingecko.CoinGeckoApiClient;
import net.xipfs.hunter.thirdpart.coingecko.domain.ExchangeRates.ExchangeRates;
import net.xipfs.hunter.thirdpart.coingecko.impl.CoinGeckoApiClientImpl;

public class ExchangeRatesExample {
    public static void main(String[] args) {

        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();

        ExchangeRates exchangeRates = client.getExchangeRates();
        System.out.println(exchangeRates);

    }
}
