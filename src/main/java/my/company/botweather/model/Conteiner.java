package my.company.botweather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Conteiner {

    private Weather[] weathers;
    private Wind wind;
    private Main main;

    public Weather[] getWeathers() {
        return weathers;
    }

    public Wind getWind() {
        return wind;
    }

    public Main getMain() {
        return main;
    }
}