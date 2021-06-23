package com.plugandroll.version1.models;

import com.plugandroll.version1.dtos.GetUserDTO;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "RedBox")
@AllArgsConstructor
@NoArgsConstructor
public class RedBox extends BaseEntity {

    @NotBlank
    private String title;

    @NotBlank
    private String story;

    @NotBlank
    private List<String> maps;

    @NotBlank
    private List<String> music;

    @NotBlank
    private List<String> npcs;

    @NotBlank
    private List<String> pcs;

    @NotBlank
    private List<String> tokens;

    private GetUserDTO creator;

}
