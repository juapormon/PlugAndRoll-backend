package com.plugandroll.version1.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Document(collection = "Forum")
@AllArgsConstructor
@NoArgsConstructor
public class Forum  extends BaseEntity{

    @NotBlank
    private String title;

    @NotEmpty
    private Set<TypeRol> type;

}
