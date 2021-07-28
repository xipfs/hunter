package net.xipfs.hunter.thirdpart.coingecko.example;

import net.xipfs.hunter.thirdpart.coingecko.CoinGeckoApiClient;
import net.xipfs.hunter.thirdpart.coingecko.domain.Events.EventCountries;
import net.xipfs.hunter.thirdpart.coingecko.domain.Events.EventTypes;
import net.xipfs.hunter.thirdpart.coingecko.domain.Events.Events;
import net.xipfs.hunter.thirdpart.coingecko.impl.CoinGeckoApiClientImpl;

public class EventsExample {
    public static void main(String[] args) {

        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();
        Events events = client.getEvents();
        System.out.println(events);

        long eventCount = events.getCount();
        System.out.println(eventCount);

        EventCountries eventCountries = client.getEventsCountries();
        System.out.println(eventCountries);

        EventTypes eventsTypes = client.getEventsTypes();
        System.out.println(eventsTypes);
    }
}
