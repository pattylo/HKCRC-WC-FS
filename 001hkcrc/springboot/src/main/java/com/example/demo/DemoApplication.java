package com.example.demo;

import com.example.demo.TCP.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext run = SpringApplication.run(DemoApplication.class, args);

		//ApplicationContext run = SpringApplication.run(CollectionApplication.class, args);
		run.getBean(SocketServer.class).start();
	}

}

//	public static void main(String[] args) {
//		ApplicationContext run = SpringApplication.run(CollectionApplication.class, args);
//		run.getBean(SocketServer.class).start();
//
//	}
