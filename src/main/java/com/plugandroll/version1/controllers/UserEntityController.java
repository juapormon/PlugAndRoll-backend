package com.plugandroll.version1.controllers;

import com.plugandroll.version1.dtos.GetUserDTO;
import com.plugandroll.version1.mappers.UserDTOConverter;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.services.UserEntityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.plugandroll.version1.models.LoginData;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserEntityController {

    private final UserEntityService userEntityService;

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> newDeveloper(@RequestBody UserEntity user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.userEntityService.signup(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginData loginData) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userEntityService.login(loginData.getUsername(), loginData.getPassword()));
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAll() {
        try {
            return ResponseEntity.ok(userEntityService.getAll());
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<GetUserDTO> whoIAm(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        try {
            return ResponseEntity.ok(UserDTOConverter
                    .UserToGetUserDTO(userEntityService.findByUsername(principal.getUsername())));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
