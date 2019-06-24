package com.libofan.disruptordemo;

import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DisruptordemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisruptordemoApplication.class, args);
    }

}
