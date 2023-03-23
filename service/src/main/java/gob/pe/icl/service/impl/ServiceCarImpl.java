/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.service.impl;

import com.jofrantoba.model.jpa.shared.UnknownException;
import gob.pe.icl.dao.inter.InterDaoCar;
import gob.pe.icl.entity.Car;
import gob.pe.icl.service.inter.InterServiceCar;
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
public class ServiceCarImpl implements InterServiceCar {

    @Autowired
    private Environment environment;

    @Autowired
    private InterDaoCar dao;

    @Override
    public Car saveCar(Car entidad) throws Exception {
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
                UnknownException excepcion = new UnknownException(ServiceCarImpl.class, ex.getMessage(), ex);
                excepcion.traceLog(true);
            }
            throw ex;
        }
    }

    @Override
    public Car getCarById(long id) throws Exception {
        try {            
            Car car = dao.findById(id); 
            return car;
        } catch (Exception ex) {
            if (environment.getProperty("environment").equalsIgnoreCase("dev")) {
                UnknownException excepcion = new UnknownException(ServiceBikeImpl.class, ex.getMessage(), ex);
                excepcion.traceLog(true);
            }
            throw ex;
        }
    }

    @Override
    public List<Car> getCarByUserId(int userId) throws Exception {
        try {            
            String[] mapOrder = {"model:desc"};
            return (List<Car>)dao.allFields("=:idUser:"+userId, mapOrder);            
        } catch (Exception ex) {
            if (environment.getProperty("environment").equalsIgnoreCase("dev")) {
                UnknownException excepcion = new UnknownException(ServiceBikeImpl.class, ex.getMessage(), ex);
                excepcion.traceLog(true);
            }
            throw ex;
        }
    }
    

}
