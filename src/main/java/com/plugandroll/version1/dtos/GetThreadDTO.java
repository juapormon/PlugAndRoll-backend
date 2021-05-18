package com.plugandroll.version1.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetThreadDTO {

    private String id;

    private String title;

}
