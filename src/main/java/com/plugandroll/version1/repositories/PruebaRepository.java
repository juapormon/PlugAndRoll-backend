package com.plugandroll.version1.repositories;

import com.plugandroll.version1.models.Prueba;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PruebaRepository extends MongoRepository<Prueba, String> {

    List<Prueba> findAll();

}
