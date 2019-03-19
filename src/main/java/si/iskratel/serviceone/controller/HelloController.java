package si.iskratel.serviceone.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
//	private static final Logger LOGGER = Logger.getLogger(HelloController.class.getName());

	@Autowired
	RestTemplate restTemplate;

	@GetMapping(value = "/sayHello")
	public String sayHelloFromServiceOne() {
		String serviceTwoGreeting = restTemplate.getForObject("http://localhost:8082/sayHello", String.class);
		LOGGER.info("Hi from service one");
		return "Hello from serviceOne and " + serviceTwoGreeting;
	}

	@GetMapping(value = "/exception")
	public String exception() {
		String response = "";
		try {
			throw new Exception("Exception has occured....");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occured:  ", e);

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTrace = sw.toString();
			LOGGER.error("Exception: ", stackTrace);
			response = stackTrace;
		}
		return response;
	}
}
