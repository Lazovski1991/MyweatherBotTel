package my.company.botweather;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class BotWeatherApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();

        SpringApplication.run(BotWeatherApplication.class, args);




    }

}
