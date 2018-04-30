package com.greenmile;

/**
 * @author Juciel Almeida
 * 
 * @class SpringBootApplicationConfig
 * 
 *        Classe responsável pela execução do Sistema.
 * 
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootApplicationConfig {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplicationConfig.class, args);
	}
}
