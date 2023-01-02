package com.vvs.javawebfluxpagesdownload;

// import java.util.List;
// import java.util.stream.Collectors;
// import java.util.stream.IntStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@SpringBootApplication
public class JavaWebfluxPagesDownloadApplication {

	private static final String PREFIX_URL = "https://forum.littleone.ru/showthread.php?t=8611633&page=";

	public static void main(String[] args) {
		SpringApplication.run(JavaWebfluxPagesDownloadApplication.class, args);

	// get pages URLs with stream Collectors
		// List<String> pages = IntStream
		// 	.range(0, 100)
		// 	.mapToObj(i -> PREFIX_URL + Integer.valueOf(i))
		// 	.collect(Collectors.toList());

		// create WebFlux WebClient
		WebClient webClient = WebClient.create();

		log.info("Start..."); // log start time
		// get pages URLs with Flux w/o stream
		Flux.range(0,100)
			.map(i -> PREFIX_URL.concat(Integer.toString(i)))
			.log()
			// get pages with WebFlux WebClient
			.flatMap(page -> webClient
				.get()
				.uri(page)
				.retrieve()
				.bodyToMono(String.class))
				.blockLast();

		log.info("Finish..."); // log finish time
	}

}
