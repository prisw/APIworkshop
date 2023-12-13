package visa.vttp.day17ws;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import visa.vttp.day17ws.service.NewsService;

@SpringBootApplication
public class Day17wsApplication implements CommandLineRunner {

	@Autowired
	NewsService newsSvc;

	public static void main(String[] args) {
		SpringApplication.run(Day17wsApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
		Map<String, String> countries = newsSvc.getCountries();
		System.out.printf("Country Codes: %s\n", countries);
	
	}
}
