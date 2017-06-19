package org.cmsfs.config.monitor;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class CmsfsHttpClient {
    public static String getMonitorConfig(String monitor, String process, String server) throws IOException {
        String url = String.format("http://conf.cmsfs.com/monitor/%s/%s/%s ", monitor, process, server);
        return httpClient(url)
    }

    public static String getMonitorConfig(String monitor, String process) throws IOException {
        String url = String.format("http://conf.cmsfs.com/monitor/%s/%s ", monitor, process);
        return httpClient(url)
    }

    public static String httpClient(String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpGet);
        return EntityUtils.toString(response.getEntity());
    }
}
