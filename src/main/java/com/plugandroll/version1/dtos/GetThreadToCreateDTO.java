package com.plugandroll.version1.dtos;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetThreadToCreateDTO {

    private String id;

    private String title;

    private Double rating;

    private LocalDateTime openDate;

    private LocalDateTime closeDate;

    private GetUserDTO creator;

    private Boolean onlyAuth;

    private String forumId;

}

