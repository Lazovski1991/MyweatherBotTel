package my.company.botweather.service;

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

    public String getWeatherName() {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city +"&appid=" + appId;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}











