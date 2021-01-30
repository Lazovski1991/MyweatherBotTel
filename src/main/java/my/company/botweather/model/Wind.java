package my.company.botweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wind {
    private String speed;

    public String getSpeed() {
        return speed;
    }
}