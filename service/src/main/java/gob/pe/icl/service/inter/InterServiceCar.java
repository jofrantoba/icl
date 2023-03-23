/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gob.pe.icl.service.inter;

import gob.pe.icl.entity.Car;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface InterServiceCar {
    Car saveCar(Car entidad)throws Exception;
    Car getCarById(long id)throws Exception;
    List<Car> getCarByUserId(int userId)throws Exception;
}
