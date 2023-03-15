/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.dao.impl;

import com.jofrantoba.model.jpa.shared.UnknownException;
import gob.pe.icl.dao.inter.InterDaoBike;
import gob.pe.icl.dao.inter.InterDaoUser;
import gob.pe.icl.entity.Bike;
import gob.pe.icl.entity.User;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 *
 * @author Usuario
 */
public class TestDaoBikeInsert extends TestBaseDao{ 
    
    @Test
    public void createEntity1() throws UnknownException{                
        Bike entity = contextEntity.getBean(Bike.class);        
        InterDaoBike dao = contextDao.getBean(DaoBike.class);  
        entity.setModel("Kawasaki");
        Transaction tx=dao.getSession().beginTransaction();
        dao.save(entity);
        tx.commit();
    }
}
