package pl.gdynia.amw.exchangeRate;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.gdynia.amw.exchangeRate.download.Download;
import pl.gdynia.amw.exchangeRate.model.ExchangeRateRepository;

@SpringBootApplication
public class ExchangeRateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRateApplication.class, args);
	}
}

