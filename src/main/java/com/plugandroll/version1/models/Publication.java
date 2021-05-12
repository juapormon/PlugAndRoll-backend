package com.plugandroll.version1.models;

import com.plugandroll.version1.dtos.GetUserDTO;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    private GetUserDTO creator;

    @DBRef
    private Thread thread;
}
