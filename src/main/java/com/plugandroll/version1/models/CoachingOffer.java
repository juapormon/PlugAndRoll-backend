package com.plugandroll.version1.models;

import com.plugandroll.version1.dtos.GetUserDTO;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Document(collection = "CoachingOffer")
@AllArgsConstructor
@NoArgsConstructor
public class CoachingOffer extends BaseEntity{

    @NotBlank
    private String title;

    @NotNull
    private CoachingType coachingType;

    @NotNull
    private Double price;

    @NotNull
    private GetUserDTO creator;


}
