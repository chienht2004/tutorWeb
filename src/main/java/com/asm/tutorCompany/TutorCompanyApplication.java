package com.asm.tutorCompany;

import com.cloudinary.Cloudinary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class TutorCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorCompanyApplication.class, args);
	}

	@Bean
	public Cloudinary cloudinaryConfig() {
		Cloudinary cloudinary = null;
		Map config = new HashMap();
		config.put("cloud_name", "dihmjd4zw");
		config.put("api_key", "599512169934984");
		config.put("api_secret", "lZrot_WgZq2A-eM84c5SjuZEf-4");
		cloudinary = new Cloudinary(config);
		return cloudinary;
	}

}





