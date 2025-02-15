package bussiness;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OllamaClient {

    private static final String API_URL = "http://localhost:11434/api/generate";

    public static String sendMessage(String message) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(API_URL);
            request.setHeader("Content-Type", "application/json");

            String json = "{ \"model\": \"dolphin-mistral\", \"prompt\": \"" + message + "\", \"stream\":false}";
            request.setEntity(new StringEntity(json));

              try (CloseableHttpResponse response = httpClient.execute(request)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "err" + e.getMessage();
        }
    }

    public static void main(String[] args) {
        String userInput = "What is the capital of France?";
        String response = sendMessage(userInput);
        System.out.println("AI Response: " + response);
    }
}
