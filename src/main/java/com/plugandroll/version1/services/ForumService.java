package com.plugandroll.version1.services;

import com.plugandroll.version1.models.Forum;
import com.plugandroll.version1.models.TypeRol;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.ForumRepository;
import com.plugandroll.version1.repositories.UserEntityRepository;
import com.plugandroll.version1.utils.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ForumService {

    private ForumRepository forumRepository;
    private UserEntityRepository userEntityRepository;

    public List<Forum> findAll(){
        return forumRepository.findAll();
    }

    public String editForum(User principal, Forum forum, String forumId) throws NotFoundException, UnauthorizedException {

        UserEntity me = this.userEntityRepository.findByUsername(principal.getUsername()).orElseThrow(NotFoundException::new);
        if(me.getRoles().contains(TypeRol.ADMIN)){
            Forum forumToUpdate = this.forumRepository.findById(forumId).orElseThrow(NotFoundException::new);
            forumToUpdate.setTitle(forum.getTitle());
            this.forumRepository.save(forumToUpdate);
        }else{
            throw new UnauthorizedException();
        }
        return "Forum was successfully edited";
    }
}
