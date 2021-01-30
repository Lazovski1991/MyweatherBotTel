package my.company.botweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {
    private String temp;
    private String feels_like;
    private String pressure;
    private String humidity;

    public String getTemp() {
        return temp;
    }

    public String getFeels_like() {
        return feels_like;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }
}