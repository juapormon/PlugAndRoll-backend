package com.plugandroll.version1.models;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@EqualsAndHashCode
public class BaseEntity {

    @Id
    private String id;


    /*
     * Entidad base de la que heredan todas las clases que se persisten
     * En MongoDB, el ID es string y no int.
     */
}