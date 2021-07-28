package net.xipfs.hunter.thirdpart.oick;

import net.xipfs.hunter.thirdpart.nomics.NomicsApiClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * description: OickApiClient <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2021/7/19 17:30 <br>
 */
public class OickApiClient {
    public static String dog() throws Exception {
        String url="https://api.oick.cn/dog/api.php";
        return makeAPICall(url,new ArrayList<NameValuePair>());
    }
    public static String du() throws Exception {
        String url="https://api.oick.cn/dutang/api.php";
        return makeAPICall(url,new ArrayList<NameValuePair>());
    }
    public static String yulu() throws Exception {
        String url="https://api.oick.cn/yulu/api.php";
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
        String str = "\"今天有点口腔溃疡 不太想舔了 和你的旧爱好好的啊 不要不开心了\"";
        System.out.println(str.replaceAll("\"",""));
        //System.out.println(OickApiClient.dog());
    }
}
