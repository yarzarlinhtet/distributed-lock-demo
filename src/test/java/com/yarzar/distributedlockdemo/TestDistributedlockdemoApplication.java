package com.yarzar.distributedlockdemo;

import org.springframework.boot.SpringApplication;

public class TestDistributedlockdemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(DistributedlockdemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
