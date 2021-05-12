package com.plugandroll.version1.services;

import com.plugandroll.version1.models.Publication;
import com.plugandroll.version1.models.Thread;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.PublicationRepository;
import com.plugandroll.version1.repositories.UserEntityRepository;
import com.plugandroll.version1.utils.UnauthorizedException;
import io.jsonwebtoken.lang.Assert;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PublicationService {

    private PublicationRepository publicationRepository;
    private UserEntityRepository userEntityRepository;

    public List<Publication> findAll(){
        List<Publication> res = publicationRepository.findAll();
        Collections.reverse(res);
        return res;
    }

    public Publication findById(String id){
        Publication publication = publicationRepository.findById(id).orElse(null);
        return publication;
    }

    public List<Publication> findByThreadId(String threadId){
        return this.publicationRepository.findByThreadId(threadId);
    }

    public String addPublication(Publication publication) throws UnauthorizedException {
        Assert.notNull(publication);
        if(publication.getThread().getCloseDate()==null) {
            this.publicationRepository.save(publication);
        }else{
            throw new UnauthorizedException();
        }
        return "Published successfully";
    }

    public String editPublication(User principal, Publication publication, String publicationId) throws ChangeSetPersister.NotFoundException, UnauthorizedException {

        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Publication publicationToUpdate = this.publicationRepository.findById(publicationId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if(me.getUsername().equals(publicationToUpdate.getCreator().getUsername())){
            publicationToUpdate.setText(publication.getText());
            this.publicationRepository.save(publicationToUpdate);
        }else{
            throw new UnauthorizedException();
        }
        return "Publication was successfully edited";
    }

    public String deletePublication(User principal, String threadId) throws ChangeSetPersister.NotFoundException, UnauthorizedException {

        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Publication publicationToDelete = this.publicationRepository.findById(threadId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if(me.getUsername().equals(publicationToDelete.getCreator().getUsername())||me.getRoles().contains("ADMIN")){
            this.publicationRepository.deleteById(threadId);
        }else{
            throw new UnauthorizedException();
        }
        return "Thread was successfully deleted";
    }
}
