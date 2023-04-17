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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
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
            Transaction tx=dao.getSession().beginTransaction();
            User user = dao.findById(id); 
            Hibernate.initialize(user.getBikes());
            Hibernate.initialize(user.getCars());
            dao.getSession().detach(user);
            tx.commit();
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
        Transaction tx=dao.getSession().beginTransaction();        
        User user = dao.findById(userId);
        if(user == null) {
            result.put("Mensaje", "no existe el usuario");
            return result;
        }
        result.put("User", user);
        List<Car> cars =(List<Car>)user.getCars();
        if(cars.isEmpty())
            result.put("Cars", "ese user no tiene coches");
        else
            result.put("Cars", cars);
        List<Bike> bikes =(List<Bike>)user.getBikes();
        if(bikes.isEmpty())
            result.put("Bikes", "ese user no tiene motos");
        else
            result.put("Bikes", bikes);
        dao.getSession().detach(user);
        tx.commit();
        return result;
    }

    @Override
    public User findUsername(String username)throws Exception{
       try {            
            Transaction tx=dao.getSession().beginTransaction();
            String mapFilter="equal:username:"+username;            
            List<User> user = (List)dao.allFields(mapFilter, null); 
            //dao.getSession().detach(user);
            tx.commit();
            return user.get(0);
        } catch (Exception ex) {
            if (environment.getProperty("environment").equalsIgnoreCase("dev")) {
                UnknownException excepcion = new UnknownException(ServiceUserImpl.class, ex.getMessage(), ex);
                excepcion.traceLog(true);
            }
            throw ex;
        }
    }

}
