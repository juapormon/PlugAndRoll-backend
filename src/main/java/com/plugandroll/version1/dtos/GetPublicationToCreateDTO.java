package com.plugandroll.version1.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPublicationToCreateDTO {

    private String id;

    private String text;

    private LocalDateTime date;

    private GetUserDTO creator;

    private String threadId;

}
