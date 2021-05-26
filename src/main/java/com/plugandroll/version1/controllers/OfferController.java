package com.plugandroll.version1.controllers;

import com.plugandroll.version1.dtos.GetPublicationToCreateDTO;
import com.plugandroll.version1.models.CoachingOffer;
import com.plugandroll.version1.models.CoachingType;
import com.plugandroll.version1.services.OfferService;
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
@RequestMapping("/offers")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OfferController {

    private OfferService offerService;

    @GetMapping("/type/{coachingType}")
    public ResponseEntity<List<CoachingOffer>> findByType(@PathVariable CoachingType coachingType){
        try{
            return ResponseEntity.ok(offerService.findByCoachingType(coachingType));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<CoachingOffer> findById(@PathVariable String offerId){
        try{
            return ResponseEntity.ok(offerService.findById(offerId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOffer(@AuthenticationPrincipal User principal, @RequestBody final CoachingOffer coachingOffer){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(offerService.addOffer(principal,coachingOffer));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (ChangeSetPersister.NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping ("/delete/{offerId}")
    public ResponseEntity<String> deleteOffer(@AuthenticationPrincipal User principal, @PathVariable String offerId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(offerService.deleteOffer(principal,offerId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (ChangeSetPersister.NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
