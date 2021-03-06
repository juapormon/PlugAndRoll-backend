package com.plugandroll.version1.services;

import com.plugandroll.version1.dtos.GetPublicationToCreateDTO;
import com.plugandroll.version1.mappers.UserDTOConverter;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public String addPublication(User principal, GetPublicationToCreateDTO getPublicationToCreateDTO) throws IllegalArgumentException, ChangeSetPersister.NotFoundException {
        String res = "";

        Assert.notNull(getPublicationToCreateDTO);
        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Thread thread = this.threadRepository.findById(getPublicationToCreateDTO.getThreadId()).orElseThrow(IllegalArgumentException::new);
        Publication publication = new Publication(getPublicationToCreateDTO.getText(),
                LocalDateTime.now(),
                UserDTOConverter.UserToGetUserDTO(me),
                thread);

        if(thread.getCloseDate()==null) {
            this.publicationRepository.save(publication);
            res = "Published successfully";
        }else{
            res = "This thread is closed, you cannot post anymore";
        }
        return res;
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

    public String deletePublication(User principal, String threadId) throws ChangeSetPersister.NotFoundException {
        String res = "";

        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Publication publicationToDelete = this.publicationRepository.findById(threadId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if(me.getUsername().equals(publicationToDelete.getCreator().getUsername())||me.getRoles().contains(TypeRol.ADMIN)){
            this.publicationRepository.deleteById(threadId);
            res = "Publication was successfully deleted";
        }else{
            res = "You're not allowed to do that";
        }
        return res;
    }

    public String deletePublicationsByThread(User principal, String threadId) throws ChangeSetPersister.NotFoundException, UnauthorizedException {

        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Thread threadDeleted = this.threadRepository.findById(threadId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if(me.getUsername().equals(threadDeleted.getCreator().getUsername())||me.getRoles().contains(TypeRol.ADMIN)){
            this.publicationRepository.deleteAllByThreadId(threadId);
        }else{
            throw new UnauthorizedException();
        }
        return "Thread was successfully deleted";
    }
}
