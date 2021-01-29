package my.company.botweather.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.company.botweather.service.GetWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@PropertySource("classpath:application.properties")
@Component
public class Bot extends TelegramLongPollingBot {

    private ObjectMapper mapper;

    private GetWeather getWeather;

    @Autowired
    public void setObjectMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setGetWeather(GetWeather getWeather) {
        this.getWeather = getWeather;
    }



    @Value("${userName}")
    private String userName;
    @Value("${token}")
    private String token;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().getText().startsWith("/pogoda")) {
            try {
                execute(new SendMessage().setChatId(update.getMessage().getChatId()).setText(getParamWeather(getWeather.getWeatherName())));
            } catch (TelegramApiException | JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return userName;
    }

    public String getBotToken() {
        return token;
    }


    public String getParamWeather(String json) throws JsonProcessingException {
        Conteiner conteiner = mapper.readValue(json, Conteiner.class);
        double temp = Math.round((Double.parseDouble(conteiner.getMain().getTemp()) - 273.15) * 100.0) / 100.0;
        double likeTemp = Math.round((Double.parseDouble(conteiner.getMain().getFeels_like()) - 273.15) * 100.0) / 100.0;
        double speed = Math.round((Double.parseDouble(conteiner.getWind().getSpeed()) * 4.05) * 100.0) / 100.0;

        String pressure = conteiner.getMain().getPressure();
        String humidity = conteiner.getMain().getHumidity();

        String mainMessage = "Погода в Берёзе:\n" + "температура воздуха " + temp + " градуса," + "\n" +
                "ощущается как " + likeTemp + " градуса," + "\n" +
                "скорость ветра " + speed + " км/ч," + "\n" +
                "давление " + pressure + " Па," + "\n" +
                "влажность " + humidity + " %.";

        return mainMessage;
    }
}
