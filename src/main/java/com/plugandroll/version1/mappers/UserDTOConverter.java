package com.plugandroll.version1.mappers;

import com.plugandroll.version1.dtos.GetUserDTO;
import com.plugandroll.version1.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {

    public static GetUserDTO UserToGetUserDTO(UserEntity user) {

        return GetUserDTO.builder().id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isPremium(user.getIsPremium())
                .roles(user.getRoles())
                .rating(user.getRating())
                .CoachedGames(user.getCoachedGames())
                .build();
    }
}