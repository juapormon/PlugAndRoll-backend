package com.plugandroll.version1.controllers;

import com.plugandroll.version1.dtos.GetUserDTO;
import com.plugandroll.version1.mappers.UserDTOConverter;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserEntityController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.login(username,password));
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAll() {
        try {
            return ResponseEntity.ok(userService.getAll());
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<GetUserDTO> whoIAm(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        try {
            return ResponseEntity.ok(UserDTOConverter
                    .UserToGetUserDTO(userService.findByUsername(principal.getUsername())));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
