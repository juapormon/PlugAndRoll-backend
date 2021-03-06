package com.plugandroll.version1.repositories;

import com.plugandroll.version1.models.Publication;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PublicationRepository extends MongoRepository<Publication, String> {

    List<Publication> findAll();

    Optional<Publication> findById(String id);

    @Query("{ 'thread.id' : ?0 }")
    Set<Publication> findByThreadId(String threadId);

    @DeleteQuery("{ 'thread.id' : ?0 }")
    void deleteAllByThreadId(String threadId);

}
