package com.plugandroll.version1.controllers;

import com.plugandroll.version1.models.Application;
import com.plugandroll.version1.models.CoachingType;
import com.plugandroll.version1.services.ApplicationService;
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
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationController {

    private ApplicationService applicationService;

    @GetMapping("/applications/")
    public ResponseEntity<List<Application>> findAll(){
        try{
            return ResponseEntity.ok(applicationService.findAll());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping("/offers/{offerId}/applications/accepted")
    public ResponseEntity<List<Application>> findAccepted(@AuthenticationPrincipal User principal, @PathVariable String offerId){
        try{
            return ResponseEntity.ok(applicationService.findAccepted(principal, offerId));
        }catch (IllegalArgumentException | ChangeSetPersister.NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @GetMapping("/offers/{offerId}/applications/pending")
    public ResponseEntity<List<Application>> findPending(@AuthenticationPrincipal User principal, @PathVariable String offerId){
        try{
            return ResponseEntity.ok(applicationService.findPending(principal, offerId));
        }catch (IllegalArgumentException | ChangeSetPersister.NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @GetMapping("/applications/{type}/myApplications")
    public ResponseEntity<List<Application>> findMyApplications(@AuthenticationPrincipal User principal, @PathVariable CoachingType type){
        try{
            return ResponseEntity.ok(applicationService.findMyApplicationsByType(principal, type));
        }catch (IllegalArgumentException | ChangeSetPersister.NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @DeleteMapping ("/offers/{offerId}/applications/{applicationId}/reject")
    public ResponseEntity<String> rejectApplication(@AuthenticationPrincipal User principal, @PathVariable String offerId, @PathVariable String applicationId){
        try{
            return ResponseEntity.ok(applicationService.rejectApplication(principal, offerId, applicationId));
        }catch (IllegalArgumentException | ChangeSetPersister.NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (UnauthorizedException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @DeleteMapping ("/applications/{applicationId}/delete")
    public ResponseEntity<String> deleteApplication(@AuthenticationPrincipal User principal, @PathVariable String applicationId){
        try{
            return ResponseEntity.ok(applicationService.deleteApplication(principal, applicationId));
        }catch (IllegalArgumentException | ChangeSetPersister.NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (UnauthorizedException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @PostMapping("/offers/{offerId}/applications/add")
    public ResponseEntity<String> addApplication(@AuthenticationPrincipal User principal, @PathVariable String offerId, @RequestBody final Application application){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.addApplication(principal, offerId, application));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (ChangeSetPersister.NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
