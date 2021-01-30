package my.company.botweather.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import my.company.botweather.service.GetWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@PropertySource("classpath:application.properties")
@Component
public class Bot extends TelegramLongPollingBot {

    private GetWeather getWeather;

    @Autowired
    public void setGetWeather(GetWeather getWeather) {
        this.getWeather = getWeather;
    }

    @Value("${userName}")
    private String userName;
    @Value("${token}")
    private String token;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
    @Value("${dateStart}")
    private String dateStr;
    Date parsingDate;

    {
        try {
            parsingDate = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().getText().startsWith("/pogoda")) {
            try {
                execute(new SendMessage().setText("Принял").setChatId(update.getMessage().getChatId()));
                Timer timer = new Timer(true);
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            execute(new SendMessage().setChatId(update.getMessage().getChatId()).setText(getWeather.result()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                };
                timer.scheduleAtFixedRate(task, parsingDate, 1000 * 60 * 60 * 24);
            } catch (TelegramApiException e) {
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

}
