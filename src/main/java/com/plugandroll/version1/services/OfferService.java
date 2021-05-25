package com.plugandroll.version1.services;

import com.plugandroll.version1.mappers.UserDTOConverter;
import com.plugandroll.version1.models.CoachingOffer;
import com.plugandroll.version1.models.CoachingType;
import com.plugandroll.version1.models.TypeRol;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.ApplicationRepostiory;
import com.plugandroll.version1.repositories.OfferRepository;
import com.plugandroll.version1.repositories.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OfferService {

    private final OfferRepository offerRepository;
    private final ApplicationRepostiory applicationRepostiory;
    private final UserEntityRepository userEntityRepository;

    public List<CoachingOffer> findByCoachingType(CoachingType coachingType){
        return this.offerRepository.findAllByCoachingType(coachingType);
    }

    public String addOffer(User principal, CoachingOffer coachingOffer) throws ChangeSetPersister.NotFoundException{
        String res;
        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        List<CoachingOffer> userOffers = this.offerRepository.findAllByCreatorUsername(me.getUsername());
        if(userOffers.size()<2){
            CoachingOffer newCoachingOffer = new CoachingOffer(coachingOffer.getTitle(),
                    coachingOffer.getCoachingType(),
                    coachingOffer.getPrice(),
                    UserDTOConverter.UserToGetUserDTO(me));

            this.offerRepository.save(newCoachingOffer);
            res = "Offer was published!";
        }else{
            res = "You already have 2 coaching offers published!";
        }
        return res;
    }

    public String deleteOffer(User principal, String offerId) throws ChangeSetPersister.NotFoundException {
        String res ;
        CoachingOffer coachingOffer = this.offerRepository.findById(offerId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        if(me.getUsername().equals(coachingOffer.getCreator().getUsername())||me.getRoles().contains(TypeRol.ADMIN)){
            if(!(this.applicationRepostiory.findByCoachingOfferId(offerId).size()==0)){
                this.applicationRepostiory.deleteAllByCoachingOfferId(offerId);
            }
            this.offerRepository.deleteById(offerId);
            res = "Offer was successfully deleted";
        }else{
            res = "You are not allowed to do that";
        }
        return res;
    }
}
