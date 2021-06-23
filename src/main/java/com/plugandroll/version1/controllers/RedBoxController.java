package com.plugandroll.version1.controllers;

import com.plugandroll.version1.models.RedBox;
import com.plugandroll.version1.services.RedBoxService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/redbox")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RedBoxController {

    private RedBoxService redBoxService;

    @GetMapping("/findAll")
    public ResponseEntity<List<RedBox>> findAll() {
        try {
            return ResponseEntity.ok(redBoxService.findAll());
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RedBox> findById(@PathVariable final String id) {
        try {
            return ResponseEntity.ok(redBoxService.findById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@AuthenticationPrincipal User principal, @RequestBody RedBox redBox) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(redBoxService.add(principal, redBox));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@AuthenticationPrincipal User principal, @PathVariable String id, @RequestBody RedBox redBox) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(redBoxService.update(principal, id, redBox));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@AuthenticationPrincipal User principal, @PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(redBoxService.delete(principal, id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
