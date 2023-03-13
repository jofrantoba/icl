/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package gob.pe.icl.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 *
 * @author Usuario
 */
@SpringBootApplication
@EnableConfigServer
public class Application {

    public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
