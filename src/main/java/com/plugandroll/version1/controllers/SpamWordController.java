package com.plugandroll.version1.controllers;

import com.plugandroll.version1.dtos.GetPublicationDTO;
import com.plugandroll.version1.dtos.GetThreadDTO;
import com.plugandroll.version1.models.CoachingOffer;
import com.plugandroll.version1.models.Forum;
import com.plugandroll.version1.services.SpamWordService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/spam")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SpamWordController {

    private SpamWordService spamWordService;

    @PostMapping("/thread")
    public ResponseEntity<Boolean> AnalyzeThread(@RequestBody GetThreadDTO threadDTO){
        try {
            return new ResponseEntity<>(spamWordService.CheckThread(threadDTO), HttpStatus.OK);
        }catch(IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/publication")
    public ResponseEntity<Boolean> AnalyzePublication(@RequestBody GetPublicationDTO publicationDTO){
        try {
            return new ResponseEntity<>(spamWordService.CheckPublication(publicationDTO), HttpStatus.OK);
        }catch(IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/offer")
    public ResponseEntity<Boolean> AnalyzeOffer(@RequestBody CoachingOffer coachingOffer){
        try {
            return new ResponseEntity<>(spamWordService.CheckOffer(coachingOffer), HttpStatus.OK);
        }catch(IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
