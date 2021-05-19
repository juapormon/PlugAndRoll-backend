package com.plugandroll.version1.services;

import com.plugandroll.version1.models.Publication;
import com.plugandroll.version1.models.Thread;
import com.plugandroll.version1.models.TypeRol;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.PublicationRepository;
import com.plugandroll.version1.repositories.ThreadRepository;
import com.plugandroll.version1.repositories.UserEntityRepository;
import com.plugandroll.version1.utils.UnauthorizedException;
import io.jsonwebtoken.lang.Assert;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PublicationService {

    private PublicationRepository publicationRepository;
    private UserEntityRepository userEntityRepository;
    private ThreadRepository threadRepository;

    public List<Publication> findAll(){
        List<Publication> res = publicationRepository.findAll();
        Collections.reverse(res);
        return res;
    }

    public Publication findById(String id){
        Publication publication = publicationRepository.findById(id).orElse(null);
        return publication;
    }

    public Set<Publication> findByThreadId(User principal, String threadId){
        Set<Publication> res = new HashSet<>();
        Thread thread = this.threadRepository.findById(threadId).orElse(null);
        if(thread.getForum().getType().size()==3){
            res = this.publicationRepository.findByThreadId(threadId);
        }else if(principal!=null){
            UserEntity user = this.userEntityRepository.findByUsername(principal.getUsername()).orElse(null);
            for (TypeRol rol : user.getRoles()) {
                if(thread.getForum().getType().contains(rol)){
                    res = this.publicationRepository.findByThreadId(threadId);
                }
            }
        }
        return res;
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

    public String deletePublicationsByThread(User principal, String threadId) throws ChangeSetPersister.NotFoundException, UnauthorizedException {

        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Thread threadDeleted = this.threadRepository.findById(threadId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if(me.getUsername().equals(threadDeleted.getCreator().getUsername())||me.getRoles().contains("ADMIN")){
            this.publicationRepository.deleteAllByThreadId(threadId);
        }else{
            throw new UnauthorizedException();
        }
        return "Thread was successfully deleted";
    }
}
