package org.abonehatirlatici.neoduyorum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NeoduyorumApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeoduyorumApplication.class, args);
    }

}
