package com.plugandroll.version1.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "Forum")
@AllArgsConstructor
@NoArgsConstructor
public class Forum  extends BaseEntity{

    @NotBlank
    private TypeRol type;

    private List<Thread> threads;
}
