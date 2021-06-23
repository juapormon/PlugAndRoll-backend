package com.plugandroll.version1.repositories;

import com.plugandroll.version1.models.RedBox;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RedBoxRepository extends MongoRepository<RedBox, String> {

    List<RedBox> findAll();

    Optional<RedBox> findById(String id);
}
