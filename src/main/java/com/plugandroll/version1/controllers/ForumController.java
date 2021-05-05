package com.plugandroll.version1.controllers;

import com.plugandroll.version1.models.Forum;
import com.plugandroll.version1.services.ForumService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/forum")
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

}
