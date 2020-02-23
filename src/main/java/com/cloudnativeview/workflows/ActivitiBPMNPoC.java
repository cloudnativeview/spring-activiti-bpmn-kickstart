package com.cloudnativeview.workflows;

import java.util.Arrays;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ActivitiBPMNPoC implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(ActivitiBPMNPoC.class);
	
	 @Autowired
     private ApplicationContext applicationContext;

	
	public static void main(String[] args) {
		SpringApplication.run(ActivitiBPMNPoC.class, args);
	}
	
	 @Override
	    public void run(String... args) throws Exception {

	        String[] beans = applicationContext.getBeanDefinitionNames();
	        Arrays.sort(beans);
	        int counter=0;
	        for (String bean : beans) {
	            log.info("[Exposed bean counter: [{}] bean: [{}]] ",++counter, bean);
	        }

	    }

}
