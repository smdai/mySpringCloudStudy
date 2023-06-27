package com.bztc.test;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPTTest {
    @Value("${chatgpt.token}")
    static String token;

    public static void main(String[] args) {
//        String token = "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";
        try {
            // 设置API端点URL和请求参数
            String endpointUrl = "https://api.openai.com/v1/engines/davinci-codex/completions";
            String prompt = "你好";
            int maxTokens = 50;

            // 创建URL对象
            URL url = new URL(endpointUrl + "?prompt=" + prompt + "&max_tokens=" + maxTokens);

            // 创建HTTP连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + token); // 替换为您的访问令牌

            // 发送请求并获取响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 解析并输出回复
                String reply = parseReplyFromResponse(response.toString());
                System.out.println("ChatGPT: " + reply);
            } else {
                System.out.println("请求失败，错误代码: " + responseCode);
            }

            // 关闭连接
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String parseReplyFromResponse(String response) {
        // 在这里解析API响应并提取回复的逻辑
        // 这里只是一个示例，直接返回API响应的文本
        return response;
    }
}


