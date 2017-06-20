package org.cmsfs.lib.api.config;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ConfigApi {
    public static String getConfig(String monitor, String process) throws IOException {
        String url = String.format("http://conf.cmsfs.com/v1/config/%s/%s", monitor, process);
        return httpClient(url);
    }

    public static String getCronServersByMonitor(String monitor) throws IOException {
        String url = String.format("http://conf.cmsfs.com/v1/servers/%s", monitor);
        return httpClient(url);
    }

    public static String getProcessConfigByMonitorAndProcess(String monitor, String process) throws IOException {
        String url = String.format("http://conf.cmsfs.com/v1/monitor/%s/config/%s", monitor, process);
        return httpClient(url);
    }

    public static String getCronSshServersByMonitor(String monitor) throws IOException {
        String url = String.format("http://conf.cmsfs.com/v1/servers/ssh/%s", monitor);
        return httpClient(url);
    }

    public static String httpClient(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpGet);
        return EntityUtils.toString(response.getEntity());
    }
}
