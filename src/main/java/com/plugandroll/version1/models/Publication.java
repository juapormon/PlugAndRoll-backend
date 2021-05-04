package com.plugandroll.version1.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Publication")
public class Publication extends  BaseEntity{

    @NotBlank
    private String text;

    @NotBlank
    @Past
    private LocalDateTime date;

    @NotBlank
    private UserEntity creator;
}
