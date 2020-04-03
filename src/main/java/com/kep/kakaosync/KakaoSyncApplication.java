package com.kep.kakaosync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class KakaoSyncApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(KakaoSyncApplication.class);
        application.addListeners(new ApplicationPidFileWriter());

        application.run(args);

	}

}
