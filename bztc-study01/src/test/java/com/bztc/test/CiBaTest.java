package com.bztc.test;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import java.util.Map;

public class CiBaTest {

    public static void main(String[] args) {
//        String token = "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";
//        try {
//            // 设置API端点URL和请求参数
//            String endpointUrl = "https://open.iciba.com/dsapi/?date=2023-05-03";
//            // 创建URL对象
//            URL url = new URL(endpointUrl);
//
//            // 创建HTTP连接
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            // 发送请求并获取响应
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String line;
//                StringBuilder response = new StringBuilder();
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//
//                // 解析并输出回复
//                Map reply = parseReplyFromResponse(response.toString());
//                System.out.println("CiBa: " + reply.get("note"));
//            } else {
//                System.out.println("请求失败，错误代码: " + responseCode);
//            }
//
//            // 关闭连接
//            connection.disconnect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String s = HttpUtil.get("https://open.iciba.com/dsapi/?date=2023-05-03");
        System.out.println(s);
    }

    public static Map parseReplyFromResponse(String response) {
        // 在这里解析API响应并提取回复的逻辑
        // 这里只是一个示例，直接返回API响应的文本
        return JSONUtil.toBean(response, Map.class);
    }
}


