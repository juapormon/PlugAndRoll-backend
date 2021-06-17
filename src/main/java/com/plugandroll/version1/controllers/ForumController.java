package com.plugandroll.version1.controllers;

import com.plugandroll.version1.models.Forum;
import com.plugandroll.version1.services.ForumService;
import com.plugandroll.version1.utils.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/forums")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ForumController {

    private ForumService forumService;

    @GetMapping("/{forumId}")
    public ResponseEntity<Forum> findById(@PathVariable String forumId){
        try{
            return ResponseEntity.ok(forumService.findById(forumId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/findByRole")
    public ResponseEntity<Set<Forum>> findByRole(@AuthenticationPrincipal User principal){
        try{
            return ResponseEntity.ok(forumService.findByRoleAuth(principal));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/findForums")
    public ResponseEntity<List<Forum>> findForums(){
        try{
            return ResponseEntity.ok(forumService.findByRoleNoAuth());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PutMapping("/edit/{forumId}")
    public ResponseEntity<String> addForum(@AuthenticationPrincipal User principal, @RequestBody Forum forum, @PathVariable String forumId){
        try{
            return ResponseEntity.ok(forumService.editForum(principal,forum, forumId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something you want does not exist");
        }catch(UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only admins have that privilege");
        }
    }

}
