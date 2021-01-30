package my.company.botweather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import my.company.botweather.model.Conteiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@PropertySource("classpath:application.properties")
@Component
public class GetWeather {

    @Value("${appId}")
    private String appId;
    @Value("${city}")
    private String city;

    private ObjectMapper mapper;

    @Autowired
    public void setObjectMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String getWeatherName() {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + appId;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
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

    public String result() throws JsonProcessingException {
        return getParamWeather(getWeatherName());
    }
}











