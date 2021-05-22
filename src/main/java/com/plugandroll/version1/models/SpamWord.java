package com.plugandroll.version1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Document(collection = "SpamWord")
@AllArgsConstructor
public class SpamWord extends BaseEntity{

    @NotBlank
    private String word;
}
