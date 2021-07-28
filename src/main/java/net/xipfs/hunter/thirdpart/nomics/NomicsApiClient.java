package net.xipfs.hunter.thirdpart.nomics;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * description: NomicsUtil <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2021/7/13 11:14 <br>
 */
public class NomicsApiClient {
    private static final String API_KEY="0793c21e2e1ec6da004d2700967dfbf3ff91a132";

    public static String getPrice(String symbol) throws Exception {
        String url="http://api.nomics.com/v1/currencies/ticker?key=%s&ids=%s&interval=1h,1d,7d&per-page=100&page=1";
        url = String.format(url,API_KEY,symbol);
        System.out.println(url);
        return makeAPICall(url,new ArrayList<NameValuePair>());
    }
    public static String makeAPICall(String uri, List<NameValuePair> parameters)
            throws Exception {
        String response_content = "";
        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());
        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        CloseableHttpResponse response = client.execute(request);
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        return response_content;
    }
    public static void main(String[] args) throws Exception {
        System.out.println(NomicsApiClient.getPrice("DODO"));
    }
}
