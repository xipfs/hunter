package net.xipfs.hunter.thirdpart.coingecko.example;

import net.xipfs.hunter.thirdpart.coingecko.CoinGeckoApiClient;
import net.xipfs.hunter.thirdpart.coingecko.domain.Status.StatusUpdates;
import net.xipfs.hunter.thirdpart.coingecko.impl.CoinGeckoApiClientImpl;

public class StatusUpdatesExample {
    public static void main(String[] args) {

        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();

        StatusUpdates statusUpdates = client.getStatusUpdates();
        System.out.println(statusUpdates);

        long totalStatusUpdates = statusUpdates.getUpdates().size();
        System.out.println(totalStatusUpdates);
    }
}
