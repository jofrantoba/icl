/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.service.impl;

import com.jofrantoba.model.jpa.shared.UnknownException;
import gob.pe.icl.dao.inter.InterDaoUser;
import gob.pe.icl.entity.User;
import gob.pe.icl.service.inter.InterServiceUser;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Transaction;
import org.springframework.core.env.Environment;

/**
 *
 * @author Usuario
 */
@Slf4j
@Service
public class ServiceUserImpl implements InterServiceUser {

    @Autowired
    private Environment environment;

    @Autowired
    private InterDaoUser dao;

    @Override
    public User saveUser(User entidad) throws Exception {
        Transaction tx = dao.getSession().beginTransaction();
        try {
            entidad.setIsPersistente(Boolean.TRUE);
            entidad.setVersion((new Date()).getTime());
            dao.save(entidad);
            //int y = 0 / 0;
            tx.commit();
            return entidad;
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            if (environment.getProperty("environment").equalsIgnoreCase("dev")) {
                UnknownException excepcion = new UnknownException(ServiceUserImpl.class, ex.getMessage(), ex);
                excepcion.traceLog(true);
            }
            throw ex;
        }
    }        

}
