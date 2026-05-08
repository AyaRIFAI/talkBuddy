package io.lingua.talkbuddy;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.lingua.talkbuddy.client.AskGroq;
import io.lingua.talkbuddy.client.AskMistral;
import io.lingua.talkbuddy.model.Contexte;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TalkbuddyApplication {


	public static void main(String[] args) {
		SpringApplication.run(TalkbuddyApplication.class, args);
	}

	@Autowired
	private AskMistral askMistral;

	@Autowired
	private AskGroq askGroq;

	@Autowired
	private Contexte contexte;
	@PostConstruct
	public void init() throws JsonProcessingException {
		askMistral.askReply(contexte);
		askGroq.askReply(contexte);
	}
}
//	public static void main(String[] args) {
//
//		SpringApplication.run(TalkbuddyApplication.class, args);
//		AskMistral askM = new AskMistral();
//		askM.askReplay();
////		Client client = new Client();
////		GenerateContentResponse response =
////				client.models.generateContent(
////						"gemini-2.0-flash",
////						"Explain how AI works in a few words",
////						null);
////
////		System.out.println(response.text());
//	}


