/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.dao.impl;

import com.jofrantoba.model.jpa.daoentity.AbstractJpaDao;
import gob.pe.icl.dao.inter.InterDaoCar;
import gob.pe.icl.entity.Car;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */
@Repository
public class DaoCar extends AbstractJpaDao<Car>
        implements InterDaoCar {
    
    public DaoCar(@Qualifier("sessionFactory")SessionFactory sessionFactory) {
        super();
        setClazz(Car.class);
        this.setSessionFactory(sessionFactory);
    }
}
