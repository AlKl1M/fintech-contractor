package com.alkl1m.contractor;

import org.springframework.boot.SpringApplication;

public class TestContractorApplication {

    public static void main(String[] args) {
        SpringApplication.from(ContractorApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
