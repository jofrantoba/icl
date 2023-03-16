/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.dao.impl;

import com.jofrantoba.model.jpa.daoentity.AbstractJpaDao;
import gob.pe.icl.dao.inter.InterDaoBike;
import gob.pe.icl.entity.Bike;
import gob.pe.icl.entity.User;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */
@Repository
public class DaoBike extends AbstractJpaDao<Bike>
        implements InterDaoBike {

    public DaoBike(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super();
        setClazz(Bike.class);
        this.setSessionFactory(sessionFactory);
    }    

}
