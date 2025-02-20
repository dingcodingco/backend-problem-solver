package ding.co.backendportfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling  // 스케줄링 기능 활성화
public class BackendportfolioApplication {
    public static void main(String[] args) {
        System.out.printf("딩코딩코 화이팅3");
        SpringApplication.run(BackendportfolioApplication.class, args);
    }

}
