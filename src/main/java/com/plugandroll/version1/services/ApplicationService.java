package com.plugandroll.version1.services;

import com.plugandroll.version1.mappers.UserDTOConverter;
import com.plugandroll.version1.models.Application;
import com.plugandroll.version1.models.CoachingOffer;
import com.plugandroll.version1.models.CoachingType;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.ApplicationRepostiory;
import com.plugandroll.version1.repositories.ForumRepository;
import com.plugandroll.version1.repositories.OfferRepository;
import com.plugandroll.version1.repositories.UserEntityRepository;
import com.plugandroll.version1.utils.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationService {

    private ApplicationRepostiory applicationRepostiory;
    private OfferRepository offerRepository;
    private UserEntityRepository userEntityRepository;

    public List<Application> findAll(){
        return this.applicationRepostiory.findAll();
    }

    public List<Application> findAccepted(User principal, String offerId) throws ChangeSetPersister.NotFoundException, UnauthorizedException {
        checkCreator(principal, offerId);
        return this.applicationRepostiory.findByAccepted(offerId);
    }

    public List<Application> findPending(User principal, String offerId) throws ChangeSetPersister.NotFoundException, UnauthorizedException {
        checkCreator(principal, offerId);
        return this.applicationRepostiory.findByPending(offerId);
    }

    public List<Application> findMyApplicationsByType(User principal, CoachingType type) throws ChangeSetPersister.NotFoundException, UnauthorizedException {
        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return this.applicationRepostiory.findAllByApplicatorUsernameAndType(me.getUsername(),type);
    }


    public String rejectApplication(User principal, String offerId, String applicationId) throws ChangeSetPersister.NotFoundException, UnauthorizedException {
       checkCreator(principal, offerId);
       this.applicationRepostiory.deleteById(applicationId);
       return "Application rejected";
    }

    public String deleteApplication(User principal, String applicationId) throws ChangeSetPersister.NotFoundException, UnauthorizedException {
        Application application = this.applicationRepostiory.findById(applicationId).orElseThrow(()->new ChangeSetPersister.NotFoundException());
        if(!principal.getUsername().equals(application.getApplicatorUsername())){
            throw new UnauthorizedException();
        }
        this.applicationRepostiory.deleteById(applicationId);
        return "Application deleted";
    }

    private void checkCreator(User principal, String offerId) throws ChangeSetPersister.NotFoundException, UnauthorizedException {
        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        CoachingOffer coachingOffer = this.offerRepository.findById(offerId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if(!me.getUsername().equals(coachingOffer.getCreator().getUsername())){
            throw new UnauthorizedException();
        }
    }

    public String addApplication(User principal, String offerId, Application application) throws ChangeSetPersister.NotFoundException{
        String res;
        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        Application offerApplicationByUser = this.applicationRepostiory.findByUsernameAndOfferId(me.getUsername(), offerId);
        if(offerApplicationByUser==null){
            application.setAccepted(false);
            CoachingOffer offer = this.offerRepository.findById(offerId).orElse(null);
            if(me.getUsername().equals(offer.getCreator().getUsername())){
                res = "You cannot apply to your own offer";
            }else{
                application.setCoachingOffer(offer);
                application.setApplicatorUsername(me.getUsername());

                this.applicationRepostiory.save(application);
                res = "You applied for the offer";
            }
        }else{
            res = "You have already applied for this offer";
        }
        return res;
    }
}