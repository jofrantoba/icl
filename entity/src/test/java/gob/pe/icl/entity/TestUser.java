/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.entity;

import gob.pe.icl.config.ConfigEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
/**
 *
 * @author Usuario
 */
@Slf4j
public class TestUser extends TestBaseEntity{
    
    @Test
    void helloWorld(){
        System.out.println("salida por consola");
        log.info(String.format("%s","Hola Mundos"));
        log.info("mensaje:{}","Hola Mundos");
    }
    
    @Test
    void callContextSpring(){   
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigEntity.class);
        User bean = context.getBean(User.class);        
        bean.setId(Long.MIN_VALUE);
        User bean1 = context.getBean(User.class);
        bean.setId(8L);
        System.out.println("bean: "+bean.getId());
        System.out.println("bean1:"+bean1.getId());
        bean1.setId(90L);
        System.out.println("bean1:"+bean1.getId());
        context.close();
    }
}
