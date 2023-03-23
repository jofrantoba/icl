/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import lombok.Data;

/**
 *
 * @author Usuario
 */
@Data
@MappedSuperclass
public abstract class GlobalEntityPkNumeric implements Serializable{
    @Id    
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")  
    protected Long id;
    @Column(name = "version")
    protected Long version;    
    @Column(name = "is_persistente")
    protected Boolean isPersistente;
    @Transient   
    protected String operacion;
}