package firsov.study.sravnitaxibot.common.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import firsov.study.sravnitaxibot.common.bean.TaxiInterface;
import firsov.study.sravnitaxibot.common.model.Coords;
import firsov.study.sravnitaxibot.common.model.yandex.Response;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class YandexTaxiService implements TaxiInterface {

    @Value("#{${taxi.yandex.url}}")
    private String apiLink;

    @Override
    public int getPrice(Coords start, Coords destination) {
        String link = apiLink
                .replace("{lo}", String.valueOf(start.getLongitude()))
                .replace("{la}", String.valueOf(start.getLatitude()))
                .replace("{d_la}", String.valueOf(destination.getLatitude()))
                .replace("{d_lo}", String.valueOf(destination.getLongitude()));

        HttpClient telegramApiClient = HttpClient.newHttpClient();
        HttpRequest telegramCallbackAnswerReq =
                HttpRequest.newBuilder(URI
                        .create(link))
                        .GET().build();
        try {
            HttpResponse<String> send = telegramApiClient.send(telegramCallbackAnswerReq, HttpResponse.BodyHandlers.ofString());
            final String regex = "\"price\":([0-9]+).";
            final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
            final Matcher matcher = pattern.matcher(send.body());
            if (matcher.find())
                return Integer.parseInt(matcher.group(1));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }
    @Override
    public String getName() {
        return "Яндекс";
    }
}
