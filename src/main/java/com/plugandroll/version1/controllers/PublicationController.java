package com.plugandroll.version1.controllers;

import com.plugandroll.version1.models.Publication;
import com.plugandroll.version1.models.Thread;
import com.plugandroll.version1.services.PublicationService;
import com.plugandroll.version1.utils.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
    public ResponseEntity<Publication> findById(@PathVariable final String id) {
        try {
            return ResponseEntity.ok(publicationService.findById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/findByThread/{threadid}")
    public ResponseEntity<List<Publication>> findByThreadId(@PathVariable final String threadId) {
        try {
            return ResponseEntity.ok(publicationService.findByThreadId(threadId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPublication(@RequestBody final Publication publication){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(publicationService.addPublication(publication));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (UnauthorizedException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This Thread is closed");
        }
    }

    @PutMapping("/edit/{publicationId}")
    public ResponseEntity<String> editPublication(@AuthenticationPrincipal User principal, @RequestBody Publication publication, @PathVariable String publicationId){
        try{
            return ResponseEntity.ok(publicationService.editPublication(principal,publication, publicationId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something is lost or does not exist");
        }catch(UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins have that privilege");
        }
    }

    @DeleteMapping("/delete/{publicationId}")
    public ResponseEntity<String> deletePublication(@AuthenticationPrincipal User principal, @PathVariable String publicationId){
        try{
            return ResponseEntity.ok(publicationService.deletePublication(principal, publicationId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something you want does not exist");
        }catch(UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins have that privilege");
        }
    }
}
