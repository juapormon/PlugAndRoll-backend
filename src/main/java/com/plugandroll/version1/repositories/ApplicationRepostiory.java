package com.plugandroll.version1.repositories;

import com.plugandroll.version1.models.Application;
import com.plugandroll.version1.models.CoachingType;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepostiory extends MongoRepository<Application, String> {

    @Query("{ 'coachingOffer.id' : ?0 }")
    List<Application> findByCoachingOfferId(String id);

    @Query("{ $and: [ { 'applicatorUsername' : ?0 }, { 'coachingOffer.coachingType' : ?1 } ] }")
    List<Application> findAllByApplicatorUsernameAndType(String username, CoachingType type);

    @DeleteQuery("{ 'coachingOffer.id' : ?0 }")
    void deleteAllByCoachingOfferId(String id);

    @Query("{ $and: [ { 'coachingOffer.id' : ?0 }, { 'accepted' : true } ] }")
    List<Application> findByAccepted(String id);

    @Query("{ $and: [ { 'coachingOffer.id' : ?0 }, { 'accepted' : false } ] }")
    List<Application> findByPending(String id);

    @Query("{ $and: [ { 'coachingOffer.id' : ?1 }, { 'applicatorUsername' : ?0 } ] }")
    Application findByUsernameAndOfferId( String username, String OfferId);
}
