package com.plugandroll.version1.repositories;

import com.plugandroll.version1.models.CoachingOffer;
import com.plugandroll.version1.models.CoachingType;
import com.plugandroll.version1.models.Publication;
import com.plugandroll.version1.models.TypeRol;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OfferRepository extends MongoRepository<CoachingOffer, String> {

    List<CoachingOffer> findAllByCoachingType(CoachingType coachingType);

    @Query("{ 'creator.username' : ?0 }")
    List<CoachingOffer> findAllByCreatorUsername(String username);



}
