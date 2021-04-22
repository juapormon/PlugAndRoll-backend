package com.plugandroll.version1.controllers;

import com.plugandroll.version1.models.Prueba;
import com.plugandroll.version1.services.PruebaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/prueba")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PruebaController {

    private PruebaService pruebaService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Prueba>> findAll(){
        try{
            return ResponseEntity.ok(pruebaService.findAll());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
