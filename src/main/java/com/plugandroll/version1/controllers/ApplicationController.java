package com.plugandroll.version1.controllers;

import com.plugandroll.version1.models.Application;
import com.plugandroll.version1.models.CoachingOffer;
import com.plugandroll.version1.models.CoachingType;
import com.plugandroll.version1.services.ApplicationService;
import com.plugandroll.version1.services.ForumService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/offers/{offerId}/applications")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationController {

    private ApplicationService applicationService;

    @GetMapping("/")
    public ResponseEntity<List<Application>> findAll(){
        try{
            return ResponseEntity.ok(applicationService.findAll());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/accepted")
    public ResponseEntity<List<Application>> findAccepted(@PathVariable String offerId){
        try{
            return ResponseEntity.ok(applicationService.findAccepted(offerId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/pending")
    public ResponseEntity<List<Application>> findPending(@PathVariable String offerId){
        try{
            return ResponseEntity.ok(applicationService.findPending(offerId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @DeleteMapping ("/{applicationId}/reject")
    public ResponseEntity<String> rejectApplication(@PathVariable String applicationId){
        try{
            return ResponseEntity.ok(applicationService.rejectApplication(applicationId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
