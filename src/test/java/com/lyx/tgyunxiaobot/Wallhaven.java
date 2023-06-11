package com.lyx.tgyunxiaobot;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author lyx
 * @createTime 2023/6/12 1:02
 */
public class Wallhaven {
    public static void main(String[] args) throws IOException {
        HttpGet httpget = new HttpGet("https://wallhaven.cc/search?categories=111&purity=110&sorting=random&order=desc&ai_art_filter=1");
        // 执行请求，获取响应
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try (httpClient; CloseableHttpResponse response = httpClient.execute(httpget)) {
            // 获取响应的实体内容
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 将实体内容转换为字符串
                String html = EntityUtils.toString(entity);
                // 将字符串中的图片地址提取出来，用&splitCode分隔
                String images = html.replaceAll("(?s).*?data-src=\"(.*?)\".*?|.*", "$1&splitCode");
                String[] strings = images.split("&splitCode");
                for (String string : strings) {
                    String fullUrl = Wallhaven.convertToFullUrl(string);
                    System.out.println("fullUrl = " + fullUrl);
                }
                System.out.println("images = " + images);
            }
        }
    }

    public static String convertToFullUrl(String thumbnailUrl) {
        // 先获取图片ID
        String id = thumbnailUrl.substring(thumbnailUrl.lastIndexOf("/") + 1, thumbnailUrl.lastIndexOf("."));

        // 构建完整图URL

        return "https://w.wallhaven.cc/full/" + id.substring(0, 2) + "/wallhaven-" + id + ".jpg";
    }
}
