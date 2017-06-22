package com.primeted.springRest;



import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.primeted.springRest.configuration.JpaConfiguration;
import com.primeted.springRest.configuration.WebConfiguration;
import com.primeted.springRest.storage.StorageService;


@Import({WebConfiguration.class,JpaConfiguration.class})
@SpringBootApplication(scanBasePackages={"com.primeted.springRest"})
public class SpringBootCRUDApp implements CommandLineRunner{
	
	@Resource
	StorageService storageService ; 
	
	public static void main(String[] args) {
       SpringApplication.run(SpringBootCRUDApp.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		storageService.init();
		
	}

}