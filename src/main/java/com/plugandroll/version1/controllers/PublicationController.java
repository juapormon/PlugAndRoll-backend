package com.plugandroll.version1.controllers;

import com.plugandroll.version1.dtos.GetPublicationDTO;
import com.plugandroll.version1.dtos.GetPublicationToCreateDTO;
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
import java.util.Set;

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

    @GetMapping("/findByThread/{threadId}")
    public ResponseEntity<Set<Publication>> findByThreadId(@AuthenticationPrincipal User principal, @PathVariable final String threadId) {
        try {
            return ResponseEntity.ok(publicationService.findByThreadId(principal, threadId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/findByThreadNoAuth/{threadId}")
    public ResponseEntity<Set<Publication>> findByThreadIdNoAuth(@PathVariable final String threadId) {
        try {
            return ResponseEntity.ok(publicationService.findByThreadId(null, threadId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPublication(@AuthenticationPrincipal User principal, @RequestBody final GetPublicationToCreateDTO getPublicationToCreateDTO){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(publicationService.addPublication(principal,getPublicationToCreateDTO));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (UnauthorizedException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This Thread is closed");
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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

    @DeleteMapping("/deleteAll/{threadId}")
    public ResponseEntity<String> deletePublicationsByThread(@AuthenticationPrincipal User principal, @PathVariable String threadId){
        try{
            return ResponseEntity.ok(publicationService.deletePublicationsByThread(principal, threadId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something you want does not exist");
        }catch(UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins have that privilege");
        }
    }
}
