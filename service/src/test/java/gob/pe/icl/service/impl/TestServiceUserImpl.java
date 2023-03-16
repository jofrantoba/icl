/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.service.impl;

import com.jofrantoba.model.jpa.shared.UnknownException;
import gob.pe.icl.entity.User;
import gob.pe.icl.service.inter.InterServiceUser;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Usuario
 */
@Log4j2
public class TestServiceUserImpl extends TestBaseService {

    @Test
    void createEntity1() throws Exception {
        log.info("Message");
        InterServiceUser service = contextService.getBean(ServiceUserImpl.class);
        User entity = contextEntity.getBean(User.class);
        entity.setName("Jonathan"); 
        entity.setEmail("chescot2302@gmail.com");
        service.saveUser(entity);
    }
}
