package com.plugandroll.version1.repositories;

import com.plugandroll.version1.models.Forum;
import com.plugandroll.version1.models.TypeRol;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepository extends MongoRepository<Forum, TypeRol> {

    List<Forum> findAll();
}
