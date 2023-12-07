package com.accesspoint.rulesengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class RulesEngineApplication implements ErrorController {

	public static void main(String[] args) {
		SpringApplication.run(RulesEngineApplication.class, args);
	}

	private static final String PATH = "/error";

	// when the PATH is not one of our predefined paths (in our front-end), it is set to /error and forwards to our index.html file in our front-end
	@RequestMapping(value = PATH)
	public String error() {
		return "forward:/index.html";
	}
}