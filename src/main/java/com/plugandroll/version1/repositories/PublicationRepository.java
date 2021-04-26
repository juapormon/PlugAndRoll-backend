package com.plugandroll.version1.repositories;

import com.plugandroll.version1.models.Publication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends MongoRepository<Publication, String> {

    List<Publication> findAll();
}
