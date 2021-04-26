package com.plugandroll.version1.controllers;

import com.plugandroll.version1.models.Publication;
import com.plugandroll.version1.services.PublicationService;
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
@RequestMapping("/publication")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PublicationController {

    private PublicationService publicationService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Publication>> findAll(){
        try{
            return ResponseEntity.ok(publicationService.findAll());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
