package com.plugandroll.version1.repositories;

import com.plugandroll.version1.models.Thread;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepository extends MongoRepository<Thread, String> {

    List<Thread> findAll();

    @Query("{ 'forum.id' : ?0 }")
    List<Thread> findByForumId(String forumId);

}
