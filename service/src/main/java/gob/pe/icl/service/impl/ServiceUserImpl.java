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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Transaction;

/**
 *
 * @author Usuario
 */
@Service
public class ServiceUserImpl implements InterServiceUser {

    @Autowired
    private InterDaoUser dao;

    @Override
    public User saveUser(User entidad) throws UnknownException {
        Transaction tx = dao.getSession().beginTransaction();        
        entidad.setIsPersistente(Boolean.TRUE);
        entidad.setVersion((new Date()).getTime());
        dao.save(entidad);
        tx.commit();
        return entidad;        
    }

}
