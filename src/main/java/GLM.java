import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GLM {
    public static void main(String[] args) {
        // 创建一个HttpClient对象
        HttpClient client = HttpClient.newHttpClient();
        // 创建一个ObjectMapper对象，用于处理JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // 创建一个HashMap，用于存储"prompt"对象的数据
        Map<String, Object> prompt = new HashMap<>();
        prompt.put("role", "user");
        prompt.put("content", "你好");

        // 创建一个HashMap，用于存储请求体的数据
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("prompt", prompt);
        requestBody.put("temperature", 0.1);

        String jsonRequestBody;
        try {
            // 使用ObjectMapper将Map转换成JSON字符串
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert map to JSON", e);
        }

        // 创建一个HttpRequest对象
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://open.bigmodel.cn/api/paas/v3/model-api/chatglm_lite/invoke")) // 将这里替换为你的API URL
                .header("Authorization", "eyJhbGciOi6IlNJR04iLCJ0eXAiOiJKV1QifQ.eyJhcGlfa2V5TczYWMzYTQ5OWQyYjRiYWNlMzA4YTcxNDciLCJleHAiOjE2ODgyOTU1NTk2NzMsInRpbWVzdGFtcCI6MTY4ODI5MTk1OTY3M30.A4gDe0n-FQNpSiP9f98akstLAFC7W-NbaLdifdp3sfQ") // 替换为你的Authorization String
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonRequestBody)) // 使用JSON字符串创建请求体
                .build();

        try {
            // 使用HttpClient发送请求，并获取HttpResponse对象
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            // 打印HTTP状态码和响应体
            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            // 打印异常信息
            e.printStackTrace();
        }
    }
}
