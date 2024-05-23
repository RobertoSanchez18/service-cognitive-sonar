package pe.edu.vallegrande.as221s5t03be.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import pe.edu.vallegrande.as221s5t03be.model.dto.TranslationRequestDto;

import java.io.IOException;

@Component
public class TranslatorText {

    /*private static final String key = "<your-translator-key>";
    private static final String location = "<your-resource-location>";*/

    private final OkHttpClient client;

    public TranslatorText() {
        this.client = new OkHttpClient();
    }

    public String translate(TranslationRequestDto translation, String key, String location) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "[{\"Text\": \"" + translation.getTranslator() + "\"}]");
        Request request = new Request.Builder()
                .url("https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&to=" + translation.getToTranslation())
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", key)
                .addHeader("Ocp-Apim-Subscription-Region", location)
                .addHeader("Content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String jsonResponse = response.body().string();
        return processTranslationResponse(jsonResponse);
    }

    private String processTranslationResponse(String jsonResponse) {
        JsonElement element = JsonParser.parseString(jsonResponse);
        JsonObject jsonObject = element.getAsJsonArray().get(0).getAsJsonObject();
        JsonObject detectedLanguage = jsonObject.getAsJsonObject("detectedLanguage");
        JsonArray translations = jsonObject.getAsJsonArray("translations");
        JsonObject translation = translations.get(0).getAsJsonObject();
        String language = detectedLanguage.get("language").getAsString();
        String text = translation.get("text").getAsString();
        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("language", language);
        resultObject.addProperty("text", text);
        return resultObject.toString();
    }
}