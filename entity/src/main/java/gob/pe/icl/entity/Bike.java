/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Usuario
 */
@Component
@Scope("prototype")
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(catalog="demotiktok",schema="demotiktok",name = "bike")
public class Bike extends GlobalEntityPkNumeric implements Serializable{
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "id_user")
    private int idUser;
}
