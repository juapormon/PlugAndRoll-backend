package com.plugandroll.version1.controllers;

import com.plugandroll.version1.dtos.GetThreadToCreateDTO;
import com.plugandroll.version1.models.Thread;
import com.plugandroll.version1.services.PublicationService;
import com.plugandroll.version1.services.ThreadService;
import com.plugandroll.version1.utils.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/threads")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ThreadController {

    private ThreadService threadService;
    private PublicationService publicationService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Thread>> findAll(){
        try{
            return ResponseEntity.ok(threadService.findAll());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{threadId}")
    public ResponseEntity<Thread> findById(@PathVariable String threadId){
        try{
            return ResponseEntity.ok(threadService.findById(threadId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (ChangeSetPersister.NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/findByForum/{forumId}")
    public ResponseEntity<List<Thread>> findByForum(@AuthenticationPrincipal User principal, @PathVariable String forumId){
        try{
            return ResponseEntity.ok(threadService.findByForum(principal, forumId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/findByForumNoAuth/{forumId}")
    public ResponseEntity<List<Thread>> findByForumNoAuth(@PathVariable String forumId){
        try{
            return ResponseEntity.ok(threadService.findByForum(null, forumId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/edit/{threadId}")
    public ResponseEntity<String> editThread(@AuthenticationPrincipal User principal, @RequestBody Thread thread, @PathVariable String threadId){
        try{
            return ResponseEntity.ok(threadService.editThread(principal,thread, threadId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something you want does not exist");
        }catch(UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins have that privilege");
        }
    }

    @PutMapping("/close/{threadId}")
    public ResponseEntity<String> closeThread(@AuthenticationPrincipal User principal, @PathVariable String threadId){
        try{
            return ResponseEntity.ok(threadService.closeThread(principal, threadId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something you want does not exist");
        }catch(UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins have that privilege");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addThread(@AuthenticationPrincipal User principal, @RequestBody GetThreadToCreateDTO getThreadToCreateDTO){
        try{
            return ResponseEntity.ok(threadService.addThread(principal,getThreadToCreateDTO));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something you want does not exist");
        }
    }

    @DeleteMapping("/delete/{threadId}")
    public ResponseEntity<String> deleteThread(@AuthenticationPrincipal User principal, @PathVariable String threadId){
        try{
            return ResponseEntity.ok(threadService.deleteThread(principal, threadId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something you want does not exist");
        }catch(UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins have that privilege");
        }
    }

}
