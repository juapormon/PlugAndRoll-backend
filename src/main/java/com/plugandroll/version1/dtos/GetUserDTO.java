package com.plugandroll.version1.dtos;

import java.util.Date;
import java.util.Set;

import com.plugandroll.version1.models.TypeRol;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserDTO {

    private String id;

    private String username;

    private String email;

    private Boolean isPremium;

    private Set<TypeRol> roles;

    private Double rating;

    private Integer CoachedGames;
}