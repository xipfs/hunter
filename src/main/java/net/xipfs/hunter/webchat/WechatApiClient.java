package net.xipfs.hunter.webchat;

import net.xipfs.hunter.webchat.client.WechatBotClient;
import net.xipfs.hunter.webchat.common.WechatBotCommon;
import net.xipfs.hunter.webchat.domain.WechatMsg;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * description: WechatApiClient <br>
 *
 * @author xie hui <br>
 * @version 1.0 <br>
 * @date 2021/7/16 17:51 <br>
 */
public class WechatApiClient {
    public static void getWeChatUserList(){
        String uri = "http://115.238.250.250:13526";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        String json="{\"type\": 5005}";
        try {
            String result = makeAPICall(uri, paratmers,json);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
    public static String makeAPICall(String uri, List<NameValuePair> parameters,String json)
            throws Exception {
        String response_content = "";
        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);
        CloseableHttpClient client = HttpClients.createDefault();
        //HttpGet request = new HttpGet(query.build());
        HttpPost request = new HttpPost(query.build());
        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        StringEntity requestEntity = new StringEntity(json,"utf-8");
        request.setEntity(requestEntity);
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
        WechatBotClient client = new WechatBotClient("ws://115.238.250.250:13526");
        client.connect();
        while(true){
            Thread.sleep(1000*10);
            WechatMsg wechatMsg = new WechatMsg();
            wechatMsg.setType(WechatBotCommon.USER_LIST);
            wechatMsg.setContent(WechatBotCommon.CONTACT_LIST);
            client.sendMsgUtil(wechatMsg);

        }
    }
}
