/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gob.pe.icl.service.inter;

import gob.pe.icl.entity.User;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public interface InterServiceUser {
    User saveUser(User entidad)throws Exception;
    User getUserById(long id)throws Exception;    
    User findUsername(String username)throws Exception;
    Map<String, Object> getUserAndVehicles(int userId) throws Exception;
}
