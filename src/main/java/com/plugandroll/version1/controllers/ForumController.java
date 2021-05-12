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

@CrossOrigin("*")
@RestController
@RequestMapping("/forums")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ForumController {

    private ForumService forumService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Forum>> findAll(){
        try{
            return ResponseEntity.ok(forumService.findAll());
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
