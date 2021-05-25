package com.plugandroll.version1.services;

import com.plugandroll.version1.models.Application;
import com.plugandroll.version1.repositories.ApplicationRepostiory;
import com.plugandroll.version1.repositories.ForumRepository;
import com.plugandroll.version1.repositories.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationService {

    private ApplicationRepostiory applicationRepostiory;
    private UserEntityRepository userEntityRepository;

    public List<Application> findAll(){
        return this.applicationRepostiory.findAll();
    }

    public List<Application> findAccepted(String offerId){
        return this.applicationRepostiory.findByAccepted(offerId);
    }

    public List<Application> findPending(String offerId){
        return this.applicationRepostiory.findByPending(offerId);
    }

    public String rejectApplication(String applicationId){
       this.applicationRepostiory.deleteById(applicationId);
       return "Application rejected";
    }
}
