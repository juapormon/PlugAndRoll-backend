package com.plugandroll.version1.models;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Publication")
public class Publication extends  BaseEntity{

    private String image;

    @NotBlank
    private String text;

    @NotBlank
    private LocalDateTime date;

    @NotBlank
    private UserEntity creator;
}
