package com.plugandroll.version1.repositories;

import com.plugandroll.version1.models.SpamWord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpamWordRepository extends MongoRepository<SpamWord, String> {

}
