package com.plugandroll.version1.dtos;

import com.plugandroll.version1.models.Forum;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetThreadToCreateDTO {

    private String id;

    private String title;

    private Integer rating;

    private LocalDateTime openDate;

    private LocalDateTime closeDate;

    private GetUserDTO creator;

    private Boolean onlyAuth;

    private String forumId;

}

