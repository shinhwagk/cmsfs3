package org.cmsfs.lib.api.config;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ConnectApi {
    public static String getConnectsByGroup(String group) throws IOException {
        String url = String.format("http://connect.cmsfs.com/v1/connect/group/jdbc/oracle/:name", group);
        return httpClient(url);
    }

    public static String getOracleConnectByName(String name) throws IOException {
        String url = String.format("http://conf.cmsfs.com/v1/connect/jdbc/oracle/%s ", name);
        return httpClient(url);
    }

    public static String getMysqlConnectByName(String name) throws IOException {
        String url = String.format("http://conf.cmsfs.com/v1/connect/jdbc/mysql/%s ", name);
        return httpClient(url);
    }

    public static String getSshConnectByName(String name) throws IOException {
        String url = String.format("http://conf.cmsfs.com/v1/connect/ssh/%s ", name);
        return httpClient(url);
    }

    private static String httpClient(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpGet);
        return EntityUtils.toString(response.getEntity());
    }
}
