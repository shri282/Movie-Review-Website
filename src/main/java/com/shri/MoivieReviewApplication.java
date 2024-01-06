package com.shri;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.shri")
public class MoivieReviewApplication {
    
	public static void main(String[] args) {
		
		SpringApplication.run(MoivieReviewApplication.class, args);	  
		
	}
	

}
            