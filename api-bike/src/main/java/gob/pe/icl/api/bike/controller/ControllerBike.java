/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gob.pe.icl.api.bike.controller;

import gob.pe.icl.entity.Bike;
import gob.pe.icl.service.inter.InterServiceBike;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("api/bike")
public class ControllerBike {
    
    @Autowired
    private InterServiceBike interServiceBike;        
    
    @PostMapping(value="save", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Bike> save(@RequestBody Bike bike) throws Exception{        
        return ResponseEntity.status(HttpStatus.CREATED).body(interServiceBike.saveBike(bike));
        
    }
    
    @GetMapping(value="/{id}")
    public ResponseEntity<Bike> getById(@PathVariable("id") int id) throws Exception{        
        Bike bike = interServiceBike.getBikeById(id);
        if(bike == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(bike);
    }
    
    
    @GetMapping("/byuser/{userId}")
    public ResponseEntity<List<Bike>> getByUserId(@PathVariable("userId") int userId)throws Exception {
        List<Bike> bikes = interServiceBike.getBikeByUserId(userId);
        if(bikes.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bikes);
    }
}
