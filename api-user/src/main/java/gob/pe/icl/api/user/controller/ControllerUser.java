/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.api.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import gob.pe.icl.api.user.feign.FeignBike;
import gob.pe.icl.api.user.feign.FeignCar;
import gob.pe.icl.entity.Bike;
import gob.pe.icl.entity.Car;
import gob.pe.icl.entity.User;
import gob.pe.icl.service.inter.InterServiceUser;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("api/user")
public class ControllerUser {
    
    @Autowired
    private InterServiceUser interServiceUser;
    
    @Autowired
    private FeignBike feignBike;
    
    @Autowired
    private FeignCar feignCar;
    
    @PostMapping(value="save", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> save(@RequestBody User user) throws Exception{        
        return ResponseEntity.status(HttpStatus.CREATED).body(interServiceUser.saveUser(user));        
    }
    
    @PostMapping(value="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getById(@PathVariable("id") int id) throws Exception{        
        User user = interServiceUser.getUserById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);        
        String json = objectMapper.writeValueAsString(user);
        return json;        
    }
    
    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId, @RequestBody Car car) throws Exception {
        User user=interServiceUser.getUserById(userId);
        if(user == null)
            return ResponseEntity.notFound().build();
        
        car.setUser(user);
        Car carNew = feignCar.saveCar(car);
        return ResponseEntity.ok(carNew);
    }

    @PostMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId, @RequestBody Bike bike) throws Exception {
        User user=interServiceUser.getUserById(userId);
        if(user == null)
            return ResponseEntity.notFound().build();
        bike.setUser(user);
        Bike bikeNew = feignBike.saveBike(bike);
        return  ResponseEntity.ok(bikeNew);
    }
    
    @PostMapping(value="/getAll/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getAllVehicles(@PathVariable("userId") int userId) throws Exception {
        Map<String, Object> result = interServiceUser.getUserAndVehicles(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);        
        String json = objectMapper.writeValueAsString(result);
        return json;        
    }

    
}
