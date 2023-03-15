/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.dao.impl;

import com.jofrantoba.model.jpa.shared.UnknownException;
import gob.pe.icl.dao.inter.InterDaoBike;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 *
 * @author Usuario
 */
@Log4j2
public class TestDaoBikeSelect extends TestBaseDao{ 
    
    @Test
    public void createSelectAll() throws UnknownException{                        
        InterDaoBike dao = contextDao.getBean(DaoBike.class);  
        Transaction tx=dao.getSession().beginTransaction();
        log.info("Size:"+dao.allFields().size());
        tx.commit();
    }
}
