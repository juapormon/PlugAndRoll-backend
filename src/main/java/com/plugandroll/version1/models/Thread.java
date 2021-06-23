package com.plugandroll.version1.models;

import com.plugandroll.version1.dtos.GetUserDTO;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection="Thread")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Thread extends BaseEntity{

    private static final long serialVersionUID = 88749523013034397L;

    @NotBlank
    @Size(max=400)
    private String title;

    @Range(min = 0 , max = 5)
    private Double rating;

    @Past
    private LocalDateTime openDate;

    @Past
    private LocalDateTime closeDate;

    private GetUserDTO creator;

    private Boolean onlyAuth;

    @DBRef
    private Forum forum;
}
