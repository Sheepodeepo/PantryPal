package com.PantryPal;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class PantryPalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PantryPalApplication.class, args);
	}
//	private static final Logger log = LoggerFactory.getLogger(PantryPalApplication.class);

//	@Bean
//	CommandLineRunner geminiTestRun(ChatClient.Builder builder){
//		return args -> {
//			var client = builder.build();
//			String response = client.prompt("Tell me an interesting fact about Google")
//					.call()
//					.content();
//			System.out.println(response);
//		};
//
//	}
}
