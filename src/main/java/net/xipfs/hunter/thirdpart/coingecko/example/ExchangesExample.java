package net.xipfs.hunter.thirdpart.coingecko.example;

import net.xipfs.hunter.thirdpart.coingecko.CoinGeckoApiClient;
import net.xipfs.hunter.thirdpart.coingecko.domain.Exchanges.ExchangeById;
import net.xipfs.hunter.thirdpart.coingecko.domain.Exchanges.Exchanges;
import net.xipfs.hunter.thirdpart.coingecko.domain.Exchanges.ExchangesList;
import net.xipfs.hunter.thirdpart.coingecko.domain.Exchanges.ExchangesTickersById;
import net.xipfs.hunter.thirdpart.coingecko.impl.CoinGeckoApiClientImpl;

import java.util.List;

public class ExchangesExample {
    public static void main(String[] args) {

        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();

        ExchangeById binance = client.getExchangesById("binance");
        System.out.println(binance);

        String country = binance.getCountry();
        System.out.println(country);

        long startYear = binance.getYearEstablished();
        System.out.println(startYear);

        String websiteUrl = binance.getUrl();
        System.out.println(websiteUrl);

        String logoUrl = binance.getImage();
        System.out.println(logoUrl);

        double tradeVolume = binance.getTradeVolume24hBtc();
        System.out.println(tradeVolume);

        ExchangesTickersById binanceTickers = client.getExchangesTickersById("binance");
        System.out.println(binanceTickers.getTickers());

        List<Exchanges> exchanges = client.getExchanges();
        System.out.println(exchanges);

        List<ExchangesList> exchangesList = client.getExchangesList();
        System.out.println(exchangesList);

        long totalExchanges = exchangesList.size();
        System.out.println(totalExchanges);

    }
}
