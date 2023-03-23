/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gob.pe.icl.service.inter;

import gob.pe.icl.entity.Bike;
import java.util.List;
/**
 *
 * @author Usuario
 */
public interface InterServiceBike {
    Bike saveBike(Bike entidad)throws Exception;
    Bike getBikeById(long id)throws Exception;
    List<Bike> getBikeByUserId(int userId)throws Exception;
}
