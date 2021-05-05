package com.plugandroll.version1.controllers;

import com.plugandroll.version1.models.Publication;
import com.plugandroll.version1.services.PublicationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/publications")
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

    @GetMapping("/{id}")
    public ResponseEntity<Publication> findById (@PathVariable final String id) {
        try {
            return ResponseEntity.ok(publicationService.findById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPublication(@PathVariable final Publication publication){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(publicationService.addPublication(publication));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
