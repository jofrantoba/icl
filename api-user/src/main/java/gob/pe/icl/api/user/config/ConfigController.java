/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.api.user.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Usuario
 */
@Configuration
@EnableFeignClients(basePackages = "gob.pe.icl.api.user.feign")
@ComponentScan(basePackages = {"gob.pe.icl.service"})
public class ConfigController {
    
}