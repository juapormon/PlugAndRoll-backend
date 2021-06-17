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
@Builder
public class Publication extends  BaseEntity{

    @NotBlank
    private String text;

    @Past
    private LocalDateTime date;

    //imagen

    private GetUserDTO creator;

    @DBRef
    private Thread thread;
}
