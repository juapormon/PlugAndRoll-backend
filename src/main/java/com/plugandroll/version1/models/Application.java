package com.plugandroll.version1.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Document(collection = "Application")
@AllArgsConstructor
@NoArgsConstructor
public class Application extends BaseEntity{

    @NotBlank
    private String applicatorUsername;

    @NotNull
    private CoachingOffer coachingOffer;

    @NotNull
    private LocalDate date;

    private Boolean accepted;
}
