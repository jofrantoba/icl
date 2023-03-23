/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.service.impl;

import com.jofrantoba.model.jpa.shared.UnknownException;
import gob.pe.icl.dao.inter.InterDaoBike;
import gob.pe.icl.dao.inter.InterDaoCar;
import gob.pe.icl.dao.inter.InterDaoUser;
import gob.pe.icl.entity.Bike;
import gob.pe.icl.entity.Car;
import gob.pe.icl.entity.User;
import gob.pe.icl.service.inter.InterServiceUser;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    @Autowired
    private InterDaoCar daoCar;
    
    @Autowired
    private InterDaoBike daoBike;
        
    @Override
    public User saveUser(User entidad) throws Exception {
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
                UnknownException excepcion = new UnknownException(ServiceUserImpl.class, ex.getMessage(), ex);
                excepcion.traceLog(true);
            }
            throw ex;
        }
    }        

     @Override
    public User getUserById(long id) throws Exception {        
        try {            
            User user = dao.findById(id); 
            return user;
        } catch (Exception ex) {
            if (environment.getProperty("environment").equalsIgnoreCase("dev")) {
                UnknownException excepcion = new UnknownException(ServiceUserImpl.class, ex.getMessage(), ex);
                excepcion.traceLog(true);
            }
            throw ex;
        }
    }

    @Override
    public Map<String, Object> getUserAndVehicles(int userId) throws Exception {
        Map<String, Object> result = new HashMap<>();
        User user = dao.findById(userId);
        if(user == null) {
            result.put("Mensaje", "no existe el usuario");
            return result;
        }
        result.put("User", user);
        String[] mapOrder = {"model:desc"};
        List<Car> cars =(List<Car>)daoCar.allFields("=:userId:"+userId, mapOrder);                    
        if(cars.isEmpty())
            result.put("Cars", "ese user no tiene coches");
        else
            result.put("Cars", cars);
        List<Bike> bikes =(List<Bike>)daoBike.allFields("=:userId:"+userId, mapOrder);                    
        if(bikes.isEmpty())
            result.put("Bikes", "ese user no tiene motos");
        else
            result.put("Bikes", bikes);
        return result;
    }

}
