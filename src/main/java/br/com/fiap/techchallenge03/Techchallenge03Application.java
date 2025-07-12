package br.com.fiap.techchallenge03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.TimeZone;

@SpringBootApplication
// TODO: Refactor to use MongoDB
//@EnableMongoRepositories
@EnableFeignClients
public class Techchallenge03Application {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(Techchallenge03Application.class, args);
    }
}
