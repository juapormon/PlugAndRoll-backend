package com.plugandroll.version1.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Prueba")
public class Prueba extends BaseEntity{

    @NotBlank
    private String image;

}
