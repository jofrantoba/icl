/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.service.impl;

import com.jofrantoba.model.jpa.shared.UnknownException;
import gob.pe.icl.dao.inter.InterDaoBike;
import gob.pe.icl.entity.Bike;
import gob.pe.icl.entity.Bike;
import gob.pe.icl.service.inter.InterServiceBike;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

/**
 *
 * @author Usuario
 */
@Slf4j
@Service
public class ServiceBikeImpl implements InterServiceBike {

    @Autowired
    private Environment environment;

    @Autowired
    private InterDaoBike dao;

    @Override
    public Bike saveBike(Bike entidad) throws Exception {
        Transaction tx = dao.getSession().beginTransaction();
        try {
            log.info("Entrando al servicio");
            entidad.setIsPersistente(Boolean.TRUE);
            entidad.setVersion((new Date()).getTime());
            dao.save(entidad);
            tx.commit();
            return entidad;
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            if (environment.getProperty("environment").equalsIgnoreCase("dev")) {
                UnknownException excepcion = new UnknownException(ServiceBikeImpl.class, ex.getMessage(), ex);
                excepcion.traceLog(true);
            }
            throw ex;
        }
    }

    @Override
    public Bike getBikeById(long id) throws Exception {
        try {            
            Bike bike = dao.findById(id); 
            return bike;
        } catch (Exception ex) {
            if (environment.getProperty("environment").equalsIgnoreCase("dev")) {
                UnknownException excepcion = new UnknownException(ServiceBikeImpl.class, ex.getMessage(), ex);
                excepcion.traceLog(true);
            }
            throw ex;
        }
    }

    @Override
    public List<Bike> getBikeByUserId(int userId) throws Exception {        
        Transaction tx = dao.getSession().beginTransaction();
        try {            
            String[] mapOrder = {"base.model:desc"};
            List<Bike> lista=(List<Bike>)dao.allFieldsJoinFilter("left:user","=:user.id:"+userId, mapOrder);            
            tx.commit();
            return lista; 
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            if (environment.getProperty("environment").equalsIgnoreCase("dev")) {
                UnknownException excepcion = new UnknownException(ServiceBikeImpl.class, ex.getMessage(), ex);
                excepcion.traceLog(true);
            }
            throw ex;
        }
    }
        
    
}
